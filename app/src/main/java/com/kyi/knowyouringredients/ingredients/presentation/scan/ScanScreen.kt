package com.kyi.knowyouringredients.ingredients.presentation.scan

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.kyi.knowyouringredients.R
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme

@Composable
fun ScanScreen(
    state: ScanScreenState,
    onAction: (ScanScreenAction) -> Unit,
    onPermissionResult: (Boolean) -> Unit,
    onInitializeCamera: (PreviewView, LifecycleOwner) -> Unit,
    onToggleFlashlight: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var previewViewRef by remember { mutableStateOf<PreviewView?>(null) }

    // Permission Launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        onPermissionResult(isGranted)
    }

    // Check and request permission
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            onPermissionResult(true)
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // Initialize camera when permission is granted and previewView is available
    LaunchedEffect(state.isCameraPermissionGranted, previewViewRef) {
        if (state.isCameraPermissionGranted && previewViewRef != null) {
            onInitializeCamera(previewViewRef!!, lifecycleOwner)
        }
    }

    // Trigger navigation when selectedProduct is non-null
    LaunchedEffect(state.selectedProduct) {
        state.selectedProduct?.let { product ->
            onAction(ScanScreenAction.OnProductClicked(product))
        }
    }

    // Reset scanning state when screen is recomposed
    LaunchedEffect(Unit) {
        onAction(ScanScreenAction.ResumeScanning)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Camera Preview or Permission Denied UI
        if (state.isCameraPermissionGranted) {
            AndroidView(
                factory = { ctx ->
                    PreviewView(ctx).also { previewViewRef = it }
                },
                modifier = Modifier.fillMaxSize(),
                update = { previewView ->
                    if (!state.isCameraReady) {
                        onInitializeCamera(previewView, lifecycleOwner)
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.camera_permission_denied),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) }
                    ) {
                        Text(stringResource(id = R.string.request_permission))
                    }
                }
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
                onClick = { onToggleFlashlight(!state.isFlashlightOn) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(20.dp)
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Icon(
                    imageVector = if (state.isFlashlightOn) Icons.Default.FlashlightOn else Icons.Default.FlashlightOff,
                    contentDescription = stringResource(
                        id = if (state.isFlashlightOn) R.string.turn_off_flashlight else R.string.turn_on_flashlight
                    ),
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

        // Error Message
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
        if (state.isCameraPermissionGranted && !state.isLoading && state.cameraError == null) {
            Text(
                text = stringResource(id = R.string.scan_instructions),
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