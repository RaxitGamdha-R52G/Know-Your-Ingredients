package com.kyi.knowyouringredients.ingredients.presentation.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyi.knowyouringredients.core.domain.util.onError
import com.kyi.knowyouringredients.core.domain.util.onSuccess
import com.kyi.knowyouringredients.ingredients.domain.ProductDataSource
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val productDataSource: ProductDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(ProductListState())
    val state = _state.onStart { loadProducts() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductListState())

    fun onAction(action: ProductListAction) {
        when (action) {
            is ProductListAction.OnProductClicked -> {
                _state.update { it.copy(selectedProduct = action.productUI) }
            }

            is ProductListAction.OnSearch -> {
                loadProducts(
                    brands = action.brands,
                    categories = action.categories,
                    nutritionGrade = action.nutritionGrade
                )
            }

            is ProductListAction.OnLoadMore -> {
                loadProducts(page = _state.value.page + 1, append = true)
            }
        }
    }

    private fun loadProducts(
        brands: String? = null,
        categories: String? = null,
        nutritionGrade: String? = null,
        page: Int = 1,
        append: Boolean = false
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            productDataSource.getProducts(
                brands = brands,
                categories = categories,
                nutritionGrade = nutritionGrade,
                page = page,
                pageSize = _state.value.pageSize
            ).onSuccess { (products, totalCount) ->
                val productUIs = products.map { ProductUI.fromDomain(it) }
                _state.update {
                    it.copy(
                        isLoading = false,
                        products = if (append) it.products + productUIs else productUIs,
                        page = page,
                        totalCount = totalCount // Use API-provided count
                    )
                }
            }.onError {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}


//class ProductListViewModel(
//    private val productDataSource: ProductDataSource
//) : ViewModel() {
//    private val _state = MutableStateFlow(ProductListState())
//    val state = _state.onStart { loadProducts() }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductListState())
//
//    fun onAction(action: ProductListAction) {
//        when (action) {
//            is ProductListAction.OnProductClicked -> {
//                _state.update { it.copy(selectedProduct = action.productUI) }
//            }
//
//            is ProductListAction.OnSearch -> {
//                loadProducts(
//                    brands = action.brands,
//                    categories = action.categories,
//                    nutritionGrade = action.nutritionGrade
//                )
//            }
//
//            is ProductListAction.OnLoadMore -> {
//                loadProducts(page = _state.value.page + 1, append = true)
//            }
////            ProductListAction.OnRefresh -> { loadProducts() }
//        }
//    }
//
//    private fun loadProducts(
//        brands: String? = null,
//        categories: String? = null,
//        nutritionGrade: String? = null,
//        page: Int = 1,
//        append: Boolean = false
//    ) {
//        viewModelScope.launch {
//            _state.update { it.copy(isLoading = true) }
//
//            productDataSource.getProducts(
//                brands = brands,
//                categories = categories,
//                nutritionGrade = nutritionGrade,
//                page = page,
//                pageSize = _state.value.pageSize
//            ).onSuccess { products ->
//                val productUIs = products.map { ProductUI.fromDomain(it) }
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        products = if (append) it.products + productUIs else productUIs,
//                        page = page,
//                        totalCount = productUIs.size + if (append) it.totalCount else 0 // Placeholder; update with API count if needed
//                    )
//                }
//            }.onError {
//                _state.update { it.copy(isLoading = false) } // No error stored in state
//            }
//        }
//    }
//}