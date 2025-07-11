package com.kyi.knowyouringredients.ingredients.presentation.product_list

import androidx.compose.runtime.Immutable
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI

@Immutable
data class ProductListState(
    val isLoading: Boolean = false,
    val products: List<ProductUI> = emptyList(),
    val selectedProduct: ProductUI? = null,
    val page: Int = 1,
    val pageSize: Int = 24,
    val totalCount: Int = 0,
    val brands: String? = null,
    val categories: String? = null,
    val nutritionGrade: String? = null,

    // Camera-related fields
    val isCameraReady: Boolean = false,
    val isCameraPermissionGranted: Boolean = false,
    val isFlashlightOn: Boolean = false,
    val hasFlashlight: Boolean = false,
    val cameraError: String? = null,
//    val isScanning: Boolean = true
)