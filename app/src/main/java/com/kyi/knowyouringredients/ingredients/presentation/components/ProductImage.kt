package com.kyi.knowyouringredients.ingredients.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.kyi.knowyouringredients.R

@Composable
fun ProductImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    size: Dp = 60.dp,
    contentDescription: String = "Product image"
) {
    var imageSource by remember { mutableStateOf<Any?>(imageUrl) }
//    val fallbackImage = R.drawable.default_img
    val fallbackImage = R.drawable.ic_default_product

    AsyncImage(
        model = imageSource ?: fallbackImage,
        contentDescription = contentDescription,
        modifier = modifier
            .size(size)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        alignment = Alignment.Center,
        onState = { state ->
            if (state is AsyncImagePainter.State.Error && imageSource != fallbackImage) {
                imageSource = fallbackImage // Switch to fallback on error
            }
        }
    )
}