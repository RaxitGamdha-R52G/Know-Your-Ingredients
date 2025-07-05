package com.kyi.knowyouringredients.ingredients.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.kyi.knowyouringredients.R
import org.koin.java.KoinJavaComponent.inject

@Composable
fun ProductImage(
    imageUrl: String?,
    size: Dp = 100.dp,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    // Log the image URL for debugging
    Log.d("ProductImage", "Loading image URL: $imageUrl")

    val imageLoader: ImageLoader by inject(
        clazz = ImageLoader::class.java,
    )

    if (imageUrl.isNullOrBlank()) {
        Image(
            painter = painterResource(id = R.drawable.ic_placeholder_image),
            contentDescription = contentDescription ?: "Placeholder image",
            modifier = modifier
                .size(size),
            contentScale = ContentScale.Fit
        )
    } else {
        AsyncImage(
            model = imageUrl,
            imageLoader = imageLoader,
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
            modifier = modifier.size(size),
            error = painterResource(id = R.drawable.ic_error_image),
            placeholder = painterResource(id = R.drawable.ic_placeholder_image),
            onError = { Log.d("ProductImage", "Error loading image: ${it.result.throwable}") }
        )
    }
}