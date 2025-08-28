package com.kyi.knowyouringredients.ingredients.presentation.viewmodels

import android.content.Context
import android.graphics.drawable.VectorDrawable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.firebase.auth.FirebaseAuth
import com.kyi.knowyouringredients.core.domain.util.Result
import com.kyi.knowyouringredients.ingredients.domain.models.HistoryItem
import com.kyi.knowyouringredients.ingredients.domain.repository.HistoryDataSource
import com.kyi.knowyouringredients.ingredients.domain.repository.ProductDataSource
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
import com.kyi.knowyouringredients.ingredients.presentation.search.SearchScreenAction
import com.kyi.knowyouringredients.ingredients.presentation.search.SearchScreenEvent
import com.kyi.knowyouringredients.ingredients.presentation.search.SearchScreenState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchViewModel(
    private val productDataSource: ProductDataSource,
    private val historyDataSource: HistoryDataSource,
    private val firebaseAuth: FirebaseAuth
) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow(SearchScreenState())
    val state = _state.stateIn(viewModelScope, SharingStarted.Lazily, SearchScreenState())

    private val _events = Channel<SearchScreenEvent>()
    val events = _events.receiveAsFlow()

    private val imageLoader: ImageLoader by inject()
    private val context: Context by inject()

    fun onAction(action: SearchScreenAction) {
        when (action) {
            is SearchScreenAction.Search -> {
                _state.update { it.copy(isLoading = true, products = emptyList(), searchPerformed = true) }
                loadProducts(
                    query = action.searchQuery,
                    page = 1,
                    append = false
                )
            }
            is SearchScreenAction.LoadMore -> {
                Log.d("SearchViewModel", "LoadMore action, page: ${_state.value.page}")
                _state.update { it.copy(isLoading = true) }
                loadProducts(
                    query = _state.value.searchQuery,
                    page = _state.value.page + 1,
                    append = true
                )
            }
            is SearchScreenAction.OnProductClicked -> {
                viewModelScope.launch {
                    saveToHistory(action.productUI)
                    selectProduct(action.productUI)
                    _events.send(SearchScreenEvent.NavigateToProductDetail(action.productUI))
                }
            }
        }
    }

    private fun saveToHistory(productUI: ProductUI) {
        viewModelScope.launch {
            firebaseAuth.currentUser?.uid?.let { userId ->
                val historyItem = HistoryItem(
                    name = productUI.productName,
                    code = productUI.code,
                    imageUrl = productUI.imageUrl ?: productUI.frontThumbUrl ?: "",
                    type = "searched"
                )
                historyDataSource.addHistoryItem(userId, historyItem)
            }
        }
    }

    private fun loadProducts(
        query: String,
        page: Int,
        append: Boolean
    ) {
        viewModelScope.launch {
            Log.d("SearchViewModel", "Loading products, page: $page, query: $query")
            val result = productDataSource.getProducts(
                searchTerm = query,
                page = page,
                pageSize = _state.value.pageSize
            )
            when (result) {
                is Result.Success -> {
                    val (products, totalCount) = result.data
                    val productUIs = products.map { product ->
                        val productUI = ProductUI.fromDomain(product)
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
                                    .allowHardware(false)
                                    .build()
                                val result = imageLoader.execute(request)
                                if (result is SuccessResult && result.drawable !is VectorDrawable) {
                                    selectedImageUrl = url
                                    Log.d("SearchViewModel", "Selected valid image URL: $url")
                                    break
                                }
                            }
                        }
                        productUI.copy(imageUrl = selectedImageUrl)
                    }
                    Log.d("SearchViewModel", "Loaded ${productUIs.size} products, totalCount: $totalCount")
                    _state.update {
                        it.copy(
                            products = if (append) it.products + productUIs else productUIs,
                            totalCount = totalCount,
                            page = page,
                            isLoading = false,
                            searchQuery = query
                        )
                    }
                }
                is Result.Error -> {
                    Log.d("SearchViewModel", "Error loading products: ${result.error}")
                    _state.update { it.copy(isLoading = false) }
                    _events.send(SearchScreenEvent.Error(result.error))
                }
            }
        }
    }

    private suspend fun selectProduct(productUI: ProductUI) {
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
                    .allowHardware(false)
                    .build()
                val result = imageLoader.execute(request)
                if (result is SuccessResult && result.drawable !is VectorDrawable) {
                    selectedImageUrl = url
                    Log.d("SearchViewModel", "Selected valid image URL: $url")
                    break
                }
            }
        }

        _state.update {
            it.copy(
                selectedProduct = productUI.copy(
                    imageUrl = selectedImageUrl
                )
            )
        }
    }

    fun clearSelectedProduct() {
        _state.update { it.copy(selectedProduct = null) }
    }
}