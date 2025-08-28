package com.kyi.knowyouringredients.ingredients.presentation.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        null -> Color.Transparent
        else -> MaterialTheme.colorScheme.errorContainer
    }
    val alignment = when (dismissState.dismissDirection) {
        null -> Alignment.Center
        else -> Alignment.CenterEnd
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