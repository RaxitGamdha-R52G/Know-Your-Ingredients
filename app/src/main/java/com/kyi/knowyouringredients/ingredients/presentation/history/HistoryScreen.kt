package com.kyi.knowyouringredients.ingredients.presentation.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme

@Composable
fun HistoryScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "History Screen PlaceHolder",
            fontSize = 25.sp,
        )
    }

}

@Preview
@Composable
private fun HistoryScreenPreview() {
    KnowYourIngredientsTheme {
        HistoryScreen()
    }

}