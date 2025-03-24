package com.kyi.knowyouringredients.ingredients.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageInfoDto(
    @SerialName("image_url") val mainImageUrl: String? = null,
    @SerialName("image_front_url") val frontImageUrl: String? = null,
    @SerialName("image_front_thumb_url") val frontThumbUrl: String? = null
)