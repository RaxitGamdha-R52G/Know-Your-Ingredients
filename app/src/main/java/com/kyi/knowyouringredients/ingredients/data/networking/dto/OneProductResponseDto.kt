package com.kyi.knowyouringredients.ingredients.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OneProductResponseDto(
    @SerialName("status") val status: Int,
    @SerialName("product") val product: ProductDto? = null
)