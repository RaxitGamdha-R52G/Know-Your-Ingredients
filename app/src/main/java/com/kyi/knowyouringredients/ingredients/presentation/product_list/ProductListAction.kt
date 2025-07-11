package com.kyi.knowyouringredients.ingredients.presentation.product_list

import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI

sealed interface ProductListAction {
    data class OnProductClicked(val productUI: ProductUI) : ProductListAction

    data class FetchProductByBarcode(val barcode: String, val productType: String = "all") :
        ProductListAction

    data class Search(
        val brands: String? = null,
        val categories: String? = null,
        val nutritionGrade: String? = null
    ) : ProductListAction

    data object LoadMore : ProductListAction

//    data object ResumeScanning : ProductListAction
}