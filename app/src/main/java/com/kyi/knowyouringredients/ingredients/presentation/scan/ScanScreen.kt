package com.kyi.knowyouringredients.ingredients.presentation.scan

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashlightOff
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListAction
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme
import com.kyi.knowyouringredients.viewmodels.ProductListViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScanScreen(
    onAction: (ProductListAction) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var previewViewRef by remember { mutableStateOf<PreviewView?>(null) }

    // Permission Launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.onPermissionResult(isGranted)
    }

    // Check and request permission
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.onPermissionResult(true)
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // Initialize camera when permission is granted and previewView is available
    LaunchedEffect(state.isCameraPermissionGranted, previewViewRef) {
        if (state.isCameraPermissionGranted && previewViewRef != null) {
            viewModel.initializeCamera(previewViewRef!!)
        }
    }

    // Handle OnProductClicked actions for navigation
    LaunchedEffect(Unit) {
        viewModel.actions.collectLatest { action ->
            if (action is ProductListAction.OnProductClicked) {
                onAction(action)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Camera Preview
        if (state.isCameraPermissionGranted) {
            AndroidView(
                factory = { ctx ->
                    PreviewView(ctx).also { previewViewRef = it }
                },
                modifier = Modifier.fillMaxSize(),
                update = { previewView ->
                    if (!state.isCameraReady) {
                        viewModel.initializeCamera(previewView)
                    }
                }
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.cameraError ?: "Camera permission is required",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Scanning Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(width = 300.dp, height = 200.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .border(3.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(20.dp))
                    .background(Color.Transparent)
            )
        }

        // Flashlight Toggle Button
        if (state.hasFlashlight) {
            Button(
                onClick = { viewModel.toggleFlashlight(!state.isFlashlightOn) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(20.dp)
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Icon(
                    imageVector = if (state.isFlashlightOn) Icons.Default.FlashlightOn else Icons.Default.FlashlightOff,
                    contentDescription = if (state.isFlashlightOn) "Turn off flashlight" else "Turn on flashlight",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Loading Indicator
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp
                )
            }
        }

        // Error Message (for camera-related errors)
        state.cameraError?.let { message ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                )
            }
        }

        // Instructions Text
        if (state.isCameraPermissionGranted && !state.isLoading) {
            Text(
                text = "Align the barcode within the frame to scan",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 25.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScanScreenPreview() {
    KnowYourIngredientsTheme {
        ScanScreen(onAction = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ScanScreenErrorPreview() {
    KnowYourIngredientsTheme {
        ScanScreen(onAction = {})
    }
}