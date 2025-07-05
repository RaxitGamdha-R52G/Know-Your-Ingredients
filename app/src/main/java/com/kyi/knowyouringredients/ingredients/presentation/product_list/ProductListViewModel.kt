//package com.kyi.knowyouringredients.ingredients.presentation.product_list
//
//import android.content.Context
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import coil.ImageLoader
//import coil.request.ImageRequest
//import com.kyi.knowyouringredients.core.domain.util.Result
//import com.kyi.knowyouringredients.ingredients.domain.ProductDataSource
//import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
//import kotlinx.coroutines.channels.Channel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.onStart
//import kotlinx.coroutines.flow.receiveAsFlow
//import kotlinx.coroutines.flow.stateIn
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import org.koin.java.KoinJavaComponent.inject
//
//class ProductListViewModel(
//    private val productDataSource: ProductDataSource
//) : ViewModel() {
//
//    private val _state = MutableStateFlow(ProductListState())
//    val state = _state
//        .onStart { loadProducts(null, null, null, 1, false) }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductListState())
//
//    private val _events = Channel<ProductListEvent>()
//    val events = _events.receiveAsFlow()
//
//    private val imageLoader: ImageLoader by inject(
//        clazz = ImageLoader::class.java
//    )
//    private val context: Context by inject(
//        clazz = Context::class.java
//    )
//
//    fun onAction(action: ProductListAction) {
//        when (action) {
//            is ProductListAction.Search -> {
//                _state.value = _state.value.copy(isLoading = true)
//                loadProducts(
//                    brands = action.brands,
//                    categories = action.categories,
//                    nutritionGrade = action.nutritionGrade,
//                    page = 1,
//                    append = false
//                )
//            }
//
//            is ProductListAction.LoadMore -> {
//                Log.d(
//                    "ProductListViewModel",
//                    "LoadMore action received, current page: ${_state.value.page}"
//                )
//                _state.value = _state.value.copy(isLoading = true)
//                loadProducts(
//                    brands = _state.value.brands,
//                    categories = _state.value.categories,
//                    nutritionGrade = _state.value.nutritionGrade,
//                    page = _state.value.page + 1,
//                    append = true
//                )
//            }
//
//            is ProductListAction.OnProductClicked -> {
//                selectProduct(action.productUI)
//            }
//        }
//    }
//
//    private fun selectProduct(productUI: ProductUI) {
//        viewModelScope.launch {
//            // Try loading images in order: imageUrl, imageFrontUrl, imageSmallUrl
//            val imageUrls = listOfNotNull(
//                productUI.mainImageUrl,
//                productUI.smallImageUrl,
//                productUI.frontThumbUrl
//            )
//            var selectedImageUrl: String? = null
//
//            for (url in imageUrls) {
//                if (url.isNotBlank()) {
//                    val request = ImageRequest.Builder(context)
//                        .data(url)
//                        .build()
//                    val result = imageLoader.execute(request)
//                    if (result.drawable != null) {
//                        selectedImageUrl = url
//                        Log.d("ProductListViewModel", "Selected valid image URL: $url")
//                        break
//                    } else {
//                        Log.d("ProductListViewModel", "Failed to load image URL: $url")
//                    }
//                }
//            }
//
//            _state.update {
//                it.copy(
//                    selectedProduct = productUI.copy(
//                        imageUrl = selectedImageUrl
//                            ?: productUI.imageUrl // Fallback to original if none load
//                    )
//                )
//            }
//        }
//    }
//
//    fun clearSelectedProduct() {
//        _state.update { it.copy(selectedProduct = null) }
//    }
//
//    private fun loadProducts(
//        brands: String?,
//        categories: String?,
//        nutritionGrade: String?,
//        page: Int,
//        append: Boolean
//    ) {
//        viewModelScope.launch {
//            val result = productDataSource.getProducts(
//                brands = brands,
//                categories = categories,
//                nutritionGrade = nutritionGrade,
//                page = page,
//                pageSize = _state.value.pageSize
//            )
//            when (result) {
//                is Result.Success -> {
//                    val (products, totalCount) = result.data
//                    val productUIs = products.map { ProductUI.fromDomain(it) }
//                    _state.value = _state.value.copy(
//                        products = if (append) _state.value.products + productUIs else productUIs,
//                        totalCount = totalCount,
//                        page = page,
//                        isLoading = false,
//                        brands = brands,
//                        categories = categories,
//                        nutritionGrade = nutritionGrade
//                    )
//                }
//
//                is Result.Error -> {
//                    _state.value = _state.value.copy(isLoading = false)
//                    _events.send(ProductListEvent.Error(result.error))
//                }
//            }
//        }
//    }
//}

package com.kyi.knowyouringredients.ingredients.presentation.product_list

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.kyi.knowyouringredients.core.domain.util.Result
import com.kyi.knowyouringredients.ingredients.domain.ProductDataSource
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProductListViewModel(
    private val productDataSource: ProductDataSource
) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow(ProductListState())
    val state = _state
        .onStart { loadProducts(null, null, null, 1, false) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductListState())

    private val _events = Channel<ProductListEvent>()
    val events = _events.receiveAsFlow()

    private val imageLoader: ImageLoader by inject()
    private val context: Context by inject()

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
                Log.d(
                    "ProductListViewModel",
                    "LoadMore action received, current page: ${_state.value.page}, totalCount: ${_state.value.totalCount}"
                )
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
                selectProduct(action.productUI)
            }
        }
    }

    private fun selectProduct(productUI: ProductUI) {
        viewModelScope.launch {
            // Try loading images in order: imageUrl, imageFrontUrl, imageSmallUrl
            val imageUrls = listOfNotNull(
                productUI.mainImageUrl,
                productUI.smallImageUrl,
                productUI.frontThumbUrl
            )
            var selectedImageUrl: String? = null

            for (url in imageUrls) {
                if (url.isNotBlank()) {
                    val request = ImageRequest.Builder(context)
                        .data(url)
                        .build()
                    val result = imageLoader.execute(request)
                    if (result.drawable != null) {
                        selectedImageUrl = url
                        Log.d("ProductListViewModel", "Selected valid image URL: $url")
                        break
                    } else {
                        Log.d("ProductListViewModel", "Failed to load image URL: $url")
                    }
                }
            }

            _state.update {
                it.copy(
                    selectedProduct = productUI.copy(
                        imageUrl = selectedImageUrl
                            ?: productUI.imageUrl // Fallback to original if none load
                    )
                )
            }
        }
    }

    fun clearSelectedProduct() {
        _state.update { it.copy(selectedProduct = null) }
    }

    private fun loadProducts(
        brands: String?,
        categories: String?,
        nutritionGrade: String?,
        page: Int,
        append: Boolean
    ) {
        viewModelScope.launch {
            Log.d("ProductListViewModel", "Loading products, page: $page, append: $append")
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
                    Log.d(
                        "ProductListViewModel",
                        "Loaded ${productUIs.size} products, totalCount: $totalCount"
                    )
                    _state.update {
                        it.copy(
                            products = if (append) it.products + productUIs else productUIs,
                            totalCount = totalCount,
                            page = page,
                            isLoading = false,
                            brands = brands,
                            categories = categories,
                            nutritionGrade = nutritionGrade
                        )
                    }
                    Log.d(
                        "ProductListViewModel",
                        "State updated, totalCount: ${_state.value.totalCount}, products: ${_state.value.products.size}"
                    )
                }

                is Result.Error -> {
                    Log.d("ProductListViewModel", "Error loading products: ${result.error}")
                    _state.value = _state.value.copy(isLoading = false)
                    _events.send(ProductListEvent.Error(result.error))
                }
            }
        }
    }
}