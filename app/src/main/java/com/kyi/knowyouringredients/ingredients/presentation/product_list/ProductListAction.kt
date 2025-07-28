package com.kyi.knowyouringredients.ingredients.presentation.product_list

//import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
import com.kyi.knowyouringredients.ingredients.presentation.scan.ScanScreenAction
import com.kyi.knowyouringredients.ingredients.presentation.search.SearchScreenAction

//sealed interface ProductListAction {
//    data class OnProductClicked(val productUI: ProductUI) : ProductListAction
//
//    data class FetchProductByBarcode(val barcode: String, val productType: String = "all") :
//        ProductListAction
//
//    data class Search(val searchTerm: String) : ProductListAction
//
//    data object LoadMore : ProductListAction
//
//    data object ResumeScanning : ProductListAction
//    data object StopScanning: ProductListAction
//}


sealed interface ProductListAction {
    data class Search(val action: SearchScreenAction) : ProductListAction
    data class Scan(val action: ScanScreenAction) : ProductListAction
}