package com.kyi.knowyouringredients.ingredients.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListAction
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListScreen
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListState

@Composable
fun SearchScreen(
    state: SearchScreenState,
    onAction: (SearchScreenAction) -> Unit,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    var searchQuery by remember { mutableStateOf(state.searchQuery) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
//            .padding(innerPadding)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            label = { Text("Search by brand or product") },
            singleLine = true,
            trailingIcon = {
                IconButton(
                    onClick = {
                        onAction(SearchScreenAction.Search(searchQuery))
                        focusManager.clearFocus()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onAction(SearchScreenAction.Search(searchQuery))
                    focusManager.clearFocus()
                }
            )
        )

        ProductListScreen(
            state = ProductListState.Search(state),
            onAction = { action ->

                when (action) {
                    is ProductListAction.Search -> onAction(action.action)
                    is ProductListAction.Scan -> {}

                }
            },
            modifier = Modifier.fillMaxSize(),
//            innerPadding = PaddingValues(bottom = innerPadding.calculateBottomPadding())
        )
    }
}