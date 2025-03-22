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
        brands: String? = "nestlé",
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
                println("Fetched ${products.size} products, totalCount: $totalCount for brands: $brands")
                _state.update {
                    it.copy(
                        isLoading = false,
                        products = if (append) it.products + productUIs else productUIs,
                        page = page,
                        totalCount = totalCount
                    )
                }
            }.onError { error ->
                println("Error fetching products: $error")
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}