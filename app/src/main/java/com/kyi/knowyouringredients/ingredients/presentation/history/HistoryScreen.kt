package com.kyi.knowyouringredients.ingredients.presentation.history

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kyi.knowyouringredients.core.presentation.util.ObserveAsEvents
import com.kyi.knowyouringredients.ingredients.presentation.history.components.HistoryListItem
import com.kyi.knowyouringredients.ingredients.presentation.viewmodels.HistoryViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = koinViewModel(),
    onNavigateToDetail: (String) -> Unit // Callback to handle navigation from MainScreen
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Triggers fetching data when the screen is first shown or the filter changes.
    LaunchedEffect(state.selectedFilter) {
        viewModel.listenForHistory(state.selectedFilter)
    }

    // Listens for one-time events from the ViewModel, like showing a snackbar or navigating.
    ObserveAsEvents(events = viewModel.events) { event ->
        when (event) {
            is HistoryEvent.NavigateToProductDetail -> onNavigateToDetail(event.productUI.code)
            is HistoryEvent.ShowError -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            is HistoryEvent.ShowUndoSnackbar -> {
                coroutineScope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "Item removed",
                        actionLabel = "Undo",
                        duration = SnackbarDuration.Long
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onUndoDelete()
                    } else {
                        viewModel.onConfirmDelete()
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Dropdown filter menu
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                TextButton(onClick = { isDropdownExpanded = true }) {
                    Text(
                        text = if (state.selectedFilter == HistoryFilter.SCANNED) "Scanned Products" else "Searched Products",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Change history filter")
                }
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Scanned Products") },
                        onClick = {
                            viewModel.onAction(HistoryAction.OnFilterChanged(HistoryFilter.SCANNED))
                            isDropdownExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Searched Products") },
                        onClick = {
                            viewModel.onAction(HistoryAction.OnFilterChanged(HistoryFilter.SEARCHED))
                            isDropdownExpanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main content area for the list, loader, or empty state message
            Box(modifier = Modifier.fillMaxSize()) {
                if (state.isLoading && state.historyItems.isEmpty()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (state.historyItems.isEmpty()) {
                    Text(
                        text = "No ${state.selectedFilter.key} products yet.",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(state.historyItems, key = { it.code }) { item ->
                            val dismissState = rememberSwipeToDismissBoxState(
                                confirmValueChange = { dismissValue ->
                                    if (dismissValue != SwipeToDismissBoxValue.Settled) {
                                        viewModel.onAction(HistoryAction.OnItemSwiped(item))
                                        true // Returning true confirms the dismiss action
                                    } else {
                                        false
                                    }
                                }
                            )

                            SwipeToDismissBox(
                                state = dismissState,
                                backgroundContent = { DismissBackground(dismissState = dismissState) },
                                enableDismissFromStartToEnd = true, // Enables right swipe
                                enableDismissFromEndToStart = true,  // Enables left swipe
                                gesturesEnabled = true
                            ) {
                                HistoryListItem(
                                    item = item,
                                    onClick = { viewModel.onAction(HistoryAction.OnProductClicked(item.code)) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * A helper composable that shows a red background with a delete icon
 * when an item is being swiped.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.errorContainer
        SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.errorContainer
        else -> Color.Transparent
    }
    val alignment = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
        SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
        else -> Alignment.Center
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 16.dp),
        contentAlignment = alignment
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = "Delete Icon",
            tint = MaterialTheme.colorScheme.onErrorContainer
        )
    }
}