package com.kyi.knowyouringredients.ingredients.presentation.scan

import androidx.compose.runtime.Immutable
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI

@Immutable
data class ScanScreenState(
    val isLoading: Boolean = false,
    val selectedProduct: ProductUI? = null,
    val isCameraReady: Boolean = false,
    val isCameraPermissionGranted: Boolean = false,
    val isFlashlightOn: Boolean = false,
    val hasFlashlight: Boolean = false,
    val cameraError: String? = null
)