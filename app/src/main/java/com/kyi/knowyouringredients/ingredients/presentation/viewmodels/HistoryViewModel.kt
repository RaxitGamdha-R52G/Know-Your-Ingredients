package com.kyi.knowyouringredients.ingredients.presentation.viewmodels

import android.content.Context
import android.graphics.drawable.VectorDrawable
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
import com.kyi.knowyouringredients.ingredients.presentation.history.HistoryAction
import com.kyi.knowyouringredients.ingredients.presentation.history.HistoryEvent
import com.kyi.knowyouringredients.ingredients.presentation.history.HistoryFilter
import com.kyi.knowyouringredients.ingredients.presentation.history.HistoryState
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HistoryViewModel(
    private val historyDataSource: HistoryDataSource,
    private val productDataSource: ProductDataSource,
    private val firebaseAuth: FirebaseAuth
) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow(HistoryState())
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    private val _events = Channel<HistoryEvent>()
    val events = _events.receiveAsFlow()

    // Add Koin components for image validation
    private val imageLoader: ImageLoader by inject()
    private val context: Context by inject()

    private var historyJob: Job? = null
    private var recentlyDeletedItem: HistoryItem? = null

    fun onAction(action: HistoryAction) {
        when (action) {
            is HistoryAction.OnFilterChanged -> {
                _state.update { it.copy(selectedFilter = action.filter) }
                listenForHistory(action.filter) // Re-fetch when filter changes
            }
            is HistoryAction.OnProductClicked -> {
                fetchProductDetails(action.code)
            }
            is HistoryAction.OnItemSwiped -> prepareToDeleteItem(action.item)        }
    }

    private fun prepareToDeleteItem(item: HistoryItem) {
        viewModelScope.launch {
            recentlyDeletedItem = item
            // Remove item from UI immediately
            _state.update {
                it.copy(historyItems = it.historyItems.filterNot { i -> i.code == item.code })
            }
            _events.send(HistoryEvent.ShowUndoSnackbar)
        }
    }

    fun onUndoDelete() {
        viewModelScope.launch {
            recentlyDeletedItem?.let { itemToRestore ->
                listenForHistory(_state.value.selectedFilter)
            }
            recentlyDeletedItem = null
        }
    }

    fun onConfirmDelete() {
        viewModelScope.launch {
            recentlyDeletedItem?.let { itemToDelete ->
                firebaseAuth.currentUser?.uid?.let { userId ->
                    historyDataSource.deleteHistoryItem(userId, itemToDelete)
                }
            }
            recentlyDeletedItem = null
        }
    }

    fun listenForHistory(filter: HistoryFilter) {
        historyJob?.cancel() // Cancel any previous listener
        _state.update { it.copy(isLoading = true) }
        val userId = firebaseAuth.currentUser?.uid
        if (userId == null) {
            _state.update { it.copy(isLoading = false, error = "User not logged in.") }
            return
        }

        historyJob = historyDataSource.getHistory(userId, filter.key)
            .onEach { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(isLoading = false, historyItems = result.data, error = null)
                        }
                    }
                    is Result.Error -> {
                        _state.update {
                            it.copy(isLoading = false, error = "Failed to load history.")
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun fetchProductDetails(code: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, productDetail = null) }
            when (val result = productDataSource.fetchProductByBarcode(code)) {
                is Result.Success -> {
                    var productUI = ProductUI.fromDomain(result.data)
                    // **THIS FIXES PROBLEM #2**
                    productUI = productUI.copy(imageUrl = validateAndGetImageUrl(productUI))

                    _state.update { it.copy(productDetail = productUI) }
                    _events.send(HistoryEvent.NavigateToProductDetail(productUI))
                }
                is Result.Error -> {
                    _events.send(HistoryEvent.ShowError("Could not fetch product details."))
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    // **HELPER FUNCTION TO FIX PROBLEM #2**
    private suspend fun validateAndGetImageUrl(productUI: ProductUI): String? {
        val imageUrls = listOfNotNull(
            productUI.mainImageUrl,
            productUI.smallImageUrl,
            productUI.frontThumbUrl
        )
        for (url in imageUrls) {
            if (url.isNotBlank()) {
                val request = ImageRequest.Builder(context)
                    .data(url).allowHardware(false).build()
                if (imageLoader.execute(request) is SuccessResult) {
                    return url
                }
            }
        }
        return null
    }

    fun clearProductDetail() {
        _state.update { it.copy(productDetail = null) }
    }
}