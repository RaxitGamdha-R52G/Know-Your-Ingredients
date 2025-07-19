//package com.kyi.knowyouringredients.viewmodels
//
//import android.content.Context
//import android.content.pm.PackageManager
//import android.util.Log
//import androidx.annotation.OptIn
//import androidx.camera.core.Camera
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.ExperimentalGetImage
//import androidx.camera.core.ImageAnalysis
//import androidx.camera.core.Preview
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.camera.view.PreviewView
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import coil.ImageLoader
//import coil.request.ImageRequest
//import com.google.mlkit.vision.barcode.BarcodeScanner
//import com.google.mlkit.vision.barcode.BarcodeScannerOptions
//import com.google.mlkit.vision.barcode.BarcodeScanning
//import com.google.mlkit.vision.barcode.common.Barcode
//import com.google.mlkit.vision.common.InputImage
//import com.kyi.knowyouringredients.core.domain.util.Result
//import com.kyi.knowyouringredients.ingredients.domain.ProductDataSource
//import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
//import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListAction
//import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListEvent
//import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListState
//import kotlinx.coroutines.channels.Channel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.onStart
//import kotlinx.coroutines.flow.receiveAsFlow
//import kotlinx.coroutines.flow.stateIn
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import org.koin.core.component.KoinComponent
//import org.koin.core.component.inject
//import java.util.concurrent.Executor
//import java.util.concurrent.Executors
//
//class ProductListViewModel(
//    private val productDataSource: ProductDataSource
//) : ViewModel(), KoinComponent {
//
//    private val _state = MutableStateFlow(ProductListState())
//    val state = _state
//        .onStart { loadProducts(null, null, null, 1, false) }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductListState())
//
//    private val _events = Channel<ProductListEvent>()
//    val events = _events.receiveAsFlow()
//
//    private val imageLoader: ImageLoader by inject()
//    private val context: Context by inject()
//    private val cameraExecutor: Executor by lazy { Executors.newSingleThreadExecutor() }
//
//    private val barcodeScanner: BarcodeScanner by lazy {
//        BarcodeScanning.getClient(
//            BarcodeScannerOptions.Builder()
//                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
//                .build()
//        )
//    }
//
//    private var cameraProvider: ProcessCameraProvider? = null
//    private var camera: Camera? = null
//
//    fun onAction(action: ProductListAction) {
//        when (action) {
//            is ProductListAction.Search -> {
//                _state.update { it.copy(isLoading = true) }
//                loadProducts(
//                    brands = action.brands,
//                    categories = action.categories,
//                    nutritionGrade = action.nutritionGrade,
//                    page = 1,
//                    append = false
//                )
//            }
//
//            is ProductListAction.LoadMore -> {
//                Log.d(
//                    "ProductListViewModel",
//                    "LoadMore action received, current page: ${_state.value.page}, totalCount: ${_state.value.totalCount}"
//                )
//                _state.update { it.copy(isLoading = true) }
//                loadProducts(
//                    brands = _state.value.brands,
//                    categories = _state.value.categories,
//                    nutritionGrade = _state.value.nutritionGrade,
//                    page = _state.value.page + 1,
//                    append = true
//                )
//            }
//
//            is ProductListAction.OnProductClicked -> {
//                selectProduct(action.productUI)
//            }
//
//            is ProductListAction.FetchProductByBarcode -> {
//                _state.update { it.copy(isLoading = true, selectedProduct = null) }
//                fetchProductByBarcode(action.barcode, action.productType)
//            }
//        }
//    }
//
//    fun onPermissionResult(isGranted: Boolean) {
//        _state.update {
//            it.copy(
//                isCameraPermissionGranted = isGranted,
//                cameraError = if (!isGranted) "Camera permission is required to scan barcodes" else null
//            )
//        }
//        if (isGranted) {
//            checkFlashlightAvailability()
//        }
//    }
//
//    @OptIn(ExperimentalGetImage::class)
//    fun initializeCamera(previewView: PreviewView) {
//        if (!_state.value.isCameraPermissionGranted) return
//
//        viewModelScope.launch {
//            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
//            cameraProvider = cameraProviderFuture.get()
//            val preview = Preview.Builder().build().also {
//                it.surfaceProvider = previewView.surfaceProvider
//            }
//            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//            val analysis = ImageAnalysis.Builder()
//                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                .build()
//                .also { analysis ->
//                    analysis.setAnalyzer(cameraExecutor) { imageProxy ->
//                        val mediaImage = imageProxy.image
//                        if (mediaImage != null) {
//                            val image = InputImage.fromMediaImage(
//                                mediaImage,
//                                imageProxy.imageInfo.rotationDegrees
//                            )
//                            barcodeScanner.process(image)
//                                .addOnSuccessListener { barcodes ->
//                                    if (barcodes.isNotEmpty()) {
//                                        val barcode = barcodes.first().rawValue
//                                        if (barcode != null) {
//                                            onAction(ProductListAction.FetchProductByBarcode(barcode))
//                                        }
//                                    }
//                                }
//                                .addOnCompleteListener { imageProxy.close() }
//                        }
//                    }
//                }
//
//            try {
//                cameraProvider?.unbindAll()
//                camera = cameraProvider?.bindToLifecycle(
//                    previewView.context as LifecycleOwner,
//                    cameraSelector,
//                    preview,
//                    analysis
//                )
//                _state.update {
//                    it.copy(
//                        isCameraReady = true,
//                        hasFlashlight = camera?.cameraInfo?.hasFlashUnit() ?: false
//                    )
//                }
//            } catch (e: Exception) {
//                Log.e("ProductListViewModel", "Camera binding failed", e)
//                _state.update { it.copy(cameraError = "Failed to initialize camera") }
//            }
//        }
//    }
//
//    fun toggleFlashlight(isEnabled: Boolean) {
//        if (camera?.cameraInfo?.hasFlashUnit() == true) {
//            camera?.cameraControl?.enableTorch(isEnabled)
//            _state.update { it.copy(isFlashlightOn = isEnabled) }
//        }
//    }
//
//    private fun checkFlashlightAvailability() {
//        val hasFlash = context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
//        _state.update { it.copy(hasFlashlight = hasFlash) }
//    }
//
//    private fun fetchProductByBarcode(barcode: String, productType: String) {
//        viewModelScope.launch {
//            Log.d("ProductListViewModel", "Fetching product with barcode: $barcode")
//            val result = productDataSource.fetchProductByBarcode(barcode, productType)
//            when (result) {
//                is Result.Success -> {
//                    val productUI = ProductUI.fromDomain(result.data)
//                    Log.d("ProductListViewModel", "Fetched product: ${productUI.productName}")
////                    selectProduct(productUI)
//                    _state.update { it.copy(isLoading = false) }
//                }
//
//                is Result.Error -> {
//                    Log.d("ProductListViewModel", "Error fetching product: ${result.error.name}")
//                    _state.update { it.copy(isLoading = false, selectedProduct = null) }
//                    _events.send(ProductListEvent.Error(result.error))
//                }
//            }
//        }
//    }
//
//    private fun selectProduct(productUI: ProductUI) {
//        viewModelScope.launch {
//            val imageUrls = listOfNotNull(
//                productUI.mainImageUrl,
//                productUI.smallImageUrl,
//                productUI.frontThumbUrl
//            )
//            var selectedImageUrl: String? = null
//
//            for (url in imageUrls) {
//                if (url.isNotBlank()) {
//                    val request = ImageRequest.Builder(context)
//                        .data(url)
//                        .build()
//                    val result = imageLoader.execute(request)
//                    if (result.drawable != null) {
//                        selectedImageUrl = url
//                        Log.d("ProductListViewModel", "Selected valid image URL: $url")
//                        break
//                    } else {
//                        Log.d("ProductListViewModel", "Failed to load image URL: $url")
//                    }
//                }
//            }
//
//            _state.update {
//                it.copy(
//                    selectedProduct = productUI.copy(
//                        imageUrl = selectedImageUrl ?: productUI.imageUrl
//                    )
//                )
//            }
//        }
//    }
//
//    fun clearSelectedProduct() {
//        _state.update { it.copy(selectedProduct = null) }
//    }
//
//    private fun loadProducts(
//        brands: String?,
//        categories: String?,
//        nutritionGrade: String?,
//        page: Int,
//        append: Boolean
//    ) {
//        viewModelScope.launch {
//            Log.d("ProductListViewModel", "Loading products, page: $page, append: $append")
//            val result = productDataSource.getProducts(
//                brands = brands,
//                categories = categories,
//                nutritionGrade = nutritionGrade,
//                page = page,
//                pageSize = _state.value.pageSize
//            )
//            when (result) {
//                is Result.Success -> {
//                    val (products, totalCount) = result.data
//                    val productUIs = products.map { ProductUI.fromDomain(it) }
//                    Log.d(
//                        "ProductListViewModel",
//                        "Loaded ${productUIs.size} products, totalCount: $totalCount"
//                    )
//                    _state.update {
//                        it.copy(
//                            products = if (append) it.products + productUIs else productUIs,
//                            totalCount = totalCount,
//                            page = page,
//                            isLoading = false,
//                            brands = brands,
//                            categories = categories,
//                            nutritionGrade = nutritionGrade
//                        )
//                    }
//                }
//
//                is Result.Error -> {
//                    Log.d("ProductListViewModel", "Error loading products: ${result.error}")
//                    _state.update { it.copy(isLoading = false) }
//                    _events.send(ProductListEvent.Error(result.error))
//                }
//            }
//        }
//    }
//}


package com.kyi.knowyouringredients.viewmodels

import android.content.Context
import android.content.pm.PackageManager
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
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.kyi.knowyouringredients.core.domain.util.Result
import com.kyi.knowyouringredients.ingredients.domain.ProductDataSource
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListAction
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListEvent
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ProductListViewModel(
    private val productDataSource: ProductDataSource
) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow(ProductListState())
    val state = _state
        .onStart { loadProducts(null, null, null, 1, false) }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductListState())
        .stateIn(viewModelScope, SharingStarted.Lazily, ProductListState())

    private val _events = Channel<ProductListEvent>()
    val events = _events.receiveAsFlow()

    private val _actions = Channel<ProductListAction>()
    val actions = _actions.receiveAsFlow()

    private val imageLoader: ImageLoader by inject()
    private val context: Context by inject()
    private val cameraExecutor: Executor by lazy { Executors.newSingleThreadExecutor() }

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
    private val debounceDurationMs = 2000L // 2 seconds debounce

    fun onAction(action: ProductListAction) {
        when (action) {
            is ProductListAction.Search -> {
                _state.update { it.copy(isLoading = true) }
                loadProducts(
                    brands = action.brands,
                    categories = action.categories,
                    nutritionGrade = action.nutritionGrade,
                    page = 1,
                    append = false
                )
            }

//            is ProductListAction.ResumeScanning -> {
//                _state.update { it.copy(isScanning = true) }
//            }

            is ProductListAction.LoadMore -> {
                Log.d(
                    "ProductListViewModel",
                    "LoadMore action received, current page: ${_state.value.page}, totalCount: ${_state.value.totalCount}"
                )
                _state.update { it.copy(isLoading = true) }
                loadProducts(
                    brands = _state.value.brands,
                    categories = _state.value.categories,
                    nutritionGrade = _state.value.nutritionGrade,
                    page = _state.value.page + 1,
                    append = true
                )
            }

            is ProductListAction.OnProductClicked -> {
                selectProduct(action.productUI)
                viewModelScope.launch {
                    _actions.send(action)
                }
            }

            is ProductListAction.FetchProductByBarcode -> {
                _state.update { it.copy(isLoading = true, selectedProduct = null) }
                fetchProductByBarcode(action.barcode, action.productType)
            }
        }
    }

    fun onPermissionResult(isGranted: Boolean) {
        _state.update {
            it.copy(
                isCameraPermissionGranted = isGranted,
                cameraError = if (!isGranted) "Camera permission is required to scan barcodes" else null
            )
        }
        if (isGranted) {
            checkFlashlightAvailability()
        }
    }
//
//    fun dismissCameraError(){
//        _state.update { it.copy(cameraError = null) }
//    }

    @OptIn(ExperimentalGetImage::class)
    fun initializeCamera(previewView: PreviewView) {
//        if (!_state.value.isCameraPermissionGranted || !_state.value.isScanning) return
        if (!_state.value.isCameraPermissionGranted) return

        viewModelScope.launch {
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
                                            val currentTime = System.currentTimeMillis()
                                            if (barcode != lastScannedBarcode || currentTime - lastScanTime >= debounceDurationMs) {
                                                lastScannedBarcode = barcode
                                                lastScanTime = currentTime
                                                onAction(
                                                    ProductListAction.FetchProductByBarcode(
                                                        barcode
                                                    )
                                                )
                                            } else {
                                                Log.d(
                                                    "ProductListViewModel",
                                                    "Debounced barcode: $barcode"
                                                )
                                            }
                                        }
                                    }
                                }
                                .addOnCompleteListener { imageProxy.close() }
                        }
                    }
                }

            try {
                cameraProvider?.unbindAll()
                camera = cameraProvider?.bindToLifecycle(
                    previewView.context as LifecycleOwner,
                    cameraSelector,
                    preview,
                    analysis
                )
                _state.update {
                    it.copy(
                        isCameraReady = true,
                        hasFlashlight = camera?.cameraInfo?.hasFlashUnit() ?: false
                    )
                }
            } catch (e: Exception) {
                Log.e("ProductListViewModel", "Camera binding failed", e)
                _state.update { it.copy(cameraError = "Failed to initialize camera") }
            }
        }
    }

    fun toggleFlashlight(isEnabled: Boolean) {
        if (camera?.cameraInfo?.hasFlashUnit() == true) {
            camera?.cameraControl?.enableTorch(isEnabled)
            _state.update { it.copy(isFlashlightOn = isEnabled) }
        }
    }

    private fun checkFlashlightAvailability() {
        val hasFlash = context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
        _state.update { it.copy(hasFlashlight = hasFlash) }
    }

    private fun fetchProductByBarcode(barcode: String, productType: String) {
        viewModelScope.launch {
            Log.d("ProductListViewModel", "Fetching product with barcode: $barcode")
            val result = productDataSource.fetchProductByBarcode(barcode, productType)
            when (result) {
                is Result.Success -> {
                    val productUI = ProductUI.fromDomain(result.data)
                    Log.d("ProductListViewModel", "Fetched product: ${productUI.productName}")
                    selectProduct(productUI)
//                    _state.update { it.copy(isLoading = false, isScanning = false) }
                    _state.update { it.copy(isLoading = false) }
                    _actions.send(ProductListAction.OnProductClicked(productUI))
                }

                is Result.Error -> {
                    Log.d("ProductListViewModel", "Error fetching product: ${result.error.name}")
                    _state.update { it.copy(isLoading = false, selectedProduct = null) }
                    _events.send(ProductListEvent.Error(result.error))
                }
            }
        }
    }

    private fun selectProduct(productUI: ProductUI) {
        viewModelScope.launch {
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
                        .build()
                    val result = imageLoader.execute(request)
                    if (result.drawable != null) {
                        selectedImageUrl = url
                        Log.d("ProductListViewModel", "Selected valid image URL: $url")
                        break
                    } else {
                        Log.d("ProductListViewModel", "Failed to load image URL: $url")
                    }
                }
            }

            _state.update {
                it.copy(
                    selectedProduct = productUI.copy(
                        imageUrl = selectedImageUrl ?: productUI.imageUrl
                    )
                )
            }
        }
    }

    fun clearSelectedProduct() {
        _state.update { it.copy(selectedProduct = null) }
    }

    private fun loadProducts(
        brands: String?,
        categories: String?,
        nutritionGrade: String?,
        page: Int,
        append: Boolean
    ) {
        viewModelScope.launch {
            Log.d("ProductListViewModel", "Loading products, page: $page, append: $append")
            val result = productDataSource.getProducts(
                brands = brands,
                categories = categories,
                nutritionGrade = nutritionGrade,
                page = page,
                pageSize = _state.value.pageSize
            )
            when (result) {
                is Result.Success -> {
                    val (products, totalCount) = result.data
                    val productUIs = products.map { ProductUI.fromDomain(it) }
                    Log.d(
                        "ProductListViewModel",
                        "Loaded ${productUIs.size} products, totalCount: $totalCount"
                    )
                    _state.update {
                        it.copy(
                            products = if (append) it.products + productUIs else productUIs,
                            totalCount = totalCount,
                            page = page,
                            isLoading = false,
                            brands = brands,
                            categories = categories,
                            nutritionGrade = nutritionGrade
                        )
                    }
                }

                is Result.Error -> {
                    Log.d("ProductListViewModel", "Error loading products: ${result.error}")
                    _state.update { it.copy(isLoading = false) }
                    _events.send(ProductListEvent.Error(result.error))
                }
            }
        }
    }
}