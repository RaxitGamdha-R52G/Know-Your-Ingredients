package com.kyi.knowyouringredients.ingredients.presentation.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyi.knowyouringredients.core.domain.util.Result
import com.kyi.knowyouringredients.ingredients.domain.ProductDataSource
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val productDataSource: ProductDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(ProductListState())
    val state: StateFlow<ProductListState> get() = _state.asStateFlow()

    private val _events = Channel<ProductListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ProductListAction) {
        when (action) {
            is ProductListAction.Search -> {
                _state.value = _state.value.copy(isLoading = true)
                loadProducts(
                    brands = action.brands,
                    categories = action.categories,
                    nutritionGrade = action.nutritionGrade,
                    page = 1,
                    append = false
                )
            }

            is ProductListAction.LoadMore -> {
                _state.value = _state.value.copy(isLoading = true)
                loadProducts(
                    brands = _state.value.brands,
                    categories = _state.value.categories,
                    nutritionGrade = _state.value.nutritionGrade,
                    page = _state.value.page + 1,
                    append = true
                )
            }

            is ProductListAction.OnProductClicked -> {
                viewModelScope.launch {
                    _events.send(ProductListEvent.NavigateToProductDetail(action.productUI))
                }
            }
        }
    }

    private fun loadProducts(
        brands: String?,
        categories: String?,
        nutritionGrade: String?,
        page: Int,
        append: Boolean
    ) {
        viewModelScope.launch {
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
                    _state.value = _state.value.copy(
                        products = if (append) _state.value.products + productUIs else productUIs,
                        totalCount = totalCount,
                        page = page,
                        isLoading = false,
                        brands = brands,
                        categories = categories,
                        nutritionGrade = nutritionGrade
                    )
                }

                is Result.Error -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _events.send(ProductListEvent.Error(result.error))
                }
            }
        }
    }
}
