package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.models.ImageInfo

data class ImageInfoUI(
    val mainImageUrl: String?,
    val frontImageUrl: String?,
    val frontThumbUrl: String?
) {
    companion object {
        fun fromDomain(imageInfo: ImageInfo?): ImageInfoUI {
            return ImageInfoUI(
                mainImageUrl = imageInfo?.mainImageUrl,
                frontImageUrl = imageInfo?.frontImageUrl,
                frontThumbUrl = imageInfo?.frontThumbUrl
            )
        }
    }
}