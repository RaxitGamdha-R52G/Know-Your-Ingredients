package com.kyi.knowyouringredients.ingredients.presentation.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {
    Text(
        text = "Search Screen Placeholder",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    KnowYourIngredientsTheme {
        SearchScreen()
    }
}