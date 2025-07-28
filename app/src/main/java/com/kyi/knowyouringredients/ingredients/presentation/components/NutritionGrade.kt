package com.kyi.knowyouringredients.ingredients.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI

@Composable
fun NutritionGrade(
    productUI: ProductUI,
    size: Dp,
    style: TextStyle,
    modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(
                color = when (productUI.nutritionGrade.lowercase()) {
                    "a" -> Color(0xFF00C853)
                    "b" -> Color(0xFF76FF03)
                    "c" -> Color(0xFFFFC107)
                    "d" -> Color(0xFFFF5722)
                    "e" -> Color(0xFFFF0000)
                    "u" -> Color(0xD2696666)
                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = productUI.nutritionGrade,
            style = style,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }

}