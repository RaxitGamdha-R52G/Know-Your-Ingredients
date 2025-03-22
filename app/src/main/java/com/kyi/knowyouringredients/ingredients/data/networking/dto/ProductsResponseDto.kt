package com.kyi.knowyouringredients.ingredients.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponseDto(
    @SerialName("count") val count: Int,
    @SerialName("page") val page: Int,
    @SerialName("page_count") val pageCount: Int,
    @SerialName("page_size") val pageSize: Int,
    @SerialName("products") val products: List<ProductDto>,
    @SerialName("skip") val skip: Int? = null
)