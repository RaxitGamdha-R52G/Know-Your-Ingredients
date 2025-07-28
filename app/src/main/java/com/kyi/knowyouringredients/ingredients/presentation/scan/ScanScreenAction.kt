package com.kyi.knowyouringredients.ingredients.presentation.scan

import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI

sealed interface ScanScreenAction {
    data class FetchProductByBarcode(val barcode: String, val productType: String = "all") :
        ScanScreenAction

    data class OnProductClicked(val productUI: ProductUI) : ScanScreenAction
    data object ResumeScanning : ScanScreenAction
}