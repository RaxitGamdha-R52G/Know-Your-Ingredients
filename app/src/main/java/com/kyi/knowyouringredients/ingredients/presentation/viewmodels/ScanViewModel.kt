package com.kyi.knowyouringredients.ingredients.presentation.viewmodels

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.VectorDrawable
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.firebase.auth.FirebaseAuth
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.kyi.knowyouringredients.R
import com.kyi.knowyouringredients.core.domain.util.Result
import com.kyi.knowyouringredients.ingredients.domain.models.HistoryItem
import com.kyi.knowyouringredients.ingredients.domain.repository.HistoryDataSource
import com.kyi.knowyouringredients.ingredients.domain.repository.ProductDataSource
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
import com.kyi.knowyouringredients.ingredients.presentation.scan.ScanScreenAction
import com.kyi.knowyouringredients.ingredients.presentation.scan.ScanScreenEvent
import com.kyi.knowyouringredients.ingredients.presentation.scan.ScanScreenState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScanViewModel(
    private val productDataSource: ProductDataSource,
    private val historyDataSource: HistoryDataSource,
    private val firebaseAuth: FirebaseAuth
) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow(ScanScreenState())
    val state = _state.stateIn(viewModelScope, SharingStarted.Lazily, ScanScreenState())

    private val _events = Channel<ScanScreenEvent>()
    val events = _events.receiveAsFlow()

    private val imageLoader: ImageLoader by inject()
    private val context: Context by inject()
    private val cameraExecutor: ExecutorService by lazy { Executors.newSingleThreadExecutor() }

    private val barcodeScanner: BarcodeScanner by lazy {
        BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .build()
        )
    }

    private var cameraProvider: ProcessCameraProvider? = null
    private var camera: Camera? = null
    private var lastScannedBarcode: String? = null
    private var lastScanTime: Long = 0
    private val debounceDurationMs = 1000L

    fun onAction(action: ScanScreenAction) {
        when (action) {
            is ScanScreenAction.FetchProductByBarcode -> {
                Log.d("ScanViewModel", "Action: FetchProductByBarcode(barcode=${action.barcode})")
                _state.update { it.copy(isLoading = true, selectedProduct = null) }
                fetchProductByBarcode(action.barcode, action.productType)
            }
            is ScanScreenAction.OnProductClicked -> {
                Log.d("ScanViewModel", "Action: OnProductClicked(product=${action.productUI.productName})")
                viewModelScope.launch {
                    selectProduct(action.productUI)
                    Log.d("ScanViewModel", "Emitting NavigateToProductDetail for ${action.productUI.productName}")
                    _events.send(ScanScreenEvent.NavigateToProductDetail(action.productUI))
                }
            }
            is ScanScreenAction.ResumeScanning -> {
                Log.d("ScanViewModel", "Action: ResumeScanning")
                _state.update { it.copy(selectedProduct = null, isLoading = false, cameraError = null) }
                lastScannedBarcode = null
                lastScanTime = 0
            }
        }
    }

    fun onPermissionResult(isGranted: Boolean) {
        Log.d("ScanViewModel", "PermissionResult: isGranted=$isGranted")
        _state.update {
            it.copy(
                isCameraPermissionGranted = isGranted,
                cameraError = if (!isGranted) context.getString(R.string.camera_permission_denied) else null
            )
        }
        if (isGranted) {
            checkFlashlightAvailability()
        }
    }

    @OptIn(ExperimentalGetImage::class)
    fun initializeCamera(previewView: PreviewView, lifecycleOwner: LifecycleOwner) {
        if (!_state.value.isCameraPermissionGranted) {
            Log.d("ScanViewModel", "Camera initialization skipped: Permission not granted")
            return
        }

        viewModelScope.launch {
            Log.d("ScanViewModel", "Initializing camera")
            try {
                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.surfaceProvider = previewView.surfaceProvider
                }
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                val analysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also { analysis ->
                        analysis.setAnalyzer(cameraExecutor) { imageProxy ->
                            val mediaImage = imageProxy.image
                            if (mediaImage != null) {
                                val image = InputImage.fromMediaImage(
                                    mediaImage,
                                    imageProxy.imageInfo.rotationDegrees
                                )
                                barcodeScanner.process(image)
                                    .addOnSuccessListener { barcodes ->
                                        if (barcodes.isNotEmpty()) {
                                            val barcode = barcodes.first().rawValue
                                            if (barcode != null) {
                                                Log.d("ScanViewModel", "Barcode detected: $barcode")
                                                val currentTime = System.currentTimeMillis()
                                                if (currentTime - lastScanTime >= debounceDurationMs) {
                                                    lastScannedBarcode = barcode
                                                    lastScanTime = currentTime
                                                    onAction(ScanScreenAction.FetchProductByBarcode(barcode))
                                                } else {
                                                    Log.d("ScanViewModel", "Barcode ignored: Within debounce period")
                                                }
                                            }
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("ScanViewModel", "Barcode scanning failed", e)
                                    }
                                    .addOnCompleteListener { imageProxy.close() }
                            }
                        }
                    }

                cameraProvider?.unbindAll()
                camera = cameraProvider?.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    analysis
                )
                Log.d("ScanViewModel", "Camera bound successfully")
                _state.update {
                    it.copy(
                        isCameraReady = true,
                        hasFlashlight = camera?.cameraInfo?.hasFlashUnit() ?: false
                    )
                }
            } catch (e: Exception) {
                Log.e("ScanViewModel", "Camera binding failed", e)
                _state.update { it.copy(cameraError = context.getString(R.string.cameraError)) }
            }
        }
    }

    fun toggleFlashlight(isEnabled: Boolean) {
        if (camera?.cameraInfo?.hasFlashUnit() == true) {
            Log.d("ScanViewModel", "Toggling flashlight: isEnabled=$isEnabled")
            camera?.cameraControl?.enableTorch(isEnabled)
            _state.update { it.copy(isFlashlightOn = isEnabled) }
        }
    }

    private fun checkFlashlightAvailability() {
        val hasFlash = context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
        Log.d("ScanViewModel", "Flashlight availability: $hasFlash")
        _state.update { it.copy(hasFlashlight = hasFlash) }
    }

    private fun fetchProductByBarcode(barcode: String, productType: String) {
        viewModelScope.launch {
            Log.d("ScanViewModel", "Fetching product with barcode: $barcode")
            val result = productDataSource.fetchProductByBarcode(barcode, productType)
            when (result) {
                is Result.Success -> {
                    val productUI = ProductUI.fromDomain(result.data)
                    saveToHistory(productUI)
                    Log.d("ScanViewModel", "Fetched product: ${productUI.productName}")
                    selectProduct(productUI) // Set selectedProduct before navigation
                    _state.update { it.copy(isLoading = false) }
                    _events.send(ScanScreenEvent.NavigateToProductDetail(productUI))
                }
                is Result.Error -> {
                    Log.d("ScanViewModel", "Error fetching product: ${result.error.name}")
                    _state.update { it.copy(isLoading = false, selectedProduct = null) }
                    _events.send(ScanScreenEvent.Error(result.error))
                }
            }
        }
    }

    private fun saveToHistory(productUI: ProductUI) {
        viewModelScope.launch {
            firebaseAuth.currentUser?.uid?.let { userId ->
                val historyItem = HistoryItem(
                    name = productUI.productName,
                    code = productUI.code,
                    imageUrl = productUI.imageUrl ?: productUI.frontThumbUrl ?: "",
                    type = "scanned"
                )
                historyDataSource.addHistoryItem(userId, historyItem)
            }
        }
    }

    private suspend fun selectProduct(productUI: ProductUI) {
        val imageUrls = listOfNotNull(
            productUI.mainImageUrl,
            productUI.smallImageUrl,
            productUI.frontThumbUrl
        )
        var selectedImageUrl: String? = null

        for (url in imageUrls) {
            if (url.isNotBlank()) {
                val request = ImageRequest.Builder(context)
                    .data(url)
                    .allowHardware(false)
                    .build()
                val result = imageLoader.execute(request)
                if (result is SuccessResult && result.drawable !is VectorDrawable) {
                    selectedImageUrl = url
                    Log.d("ScanViewModel", "Selected valid image URL: $url")
                    break
                }
            }
        }

        _state.update {
            it.copy(
                selectedProduct = productUI.copy(
                    imageUrl = selectedImageUrl
                )
            )
        }
        Log.d("ScanViewModel", "Selected product set: ${productUI.productName}")
    }

    fun clearSelectedProduct() {
        _state.update { it.copy(selectedProduct = null) }
        Log.d("ScanViewModel", "Cleared selected product")
    }

    override fun onCleared() {
        super.onCleared()
        cameraProvider?.unbindAll()
        cameraExecutor.shutdown()
        Log.d("ScanViewModel", "ViewModel cleared, camera unbound, executor shutdown")
    }
}