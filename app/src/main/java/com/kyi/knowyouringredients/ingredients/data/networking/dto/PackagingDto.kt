package com.kyi.knowyouringredients.ingredients.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PackagingDto(
    @SerialName("material") val material: String? = null,
    @SerialName("number_of_units") val numberOfUnits: String? = null,
    @SerialName("quantity_per_unit") val quantityPerUnit: String? = null,
    @SerialName("shape") val shape: String? = null,
    @SerialName("recycling") val recycling: String? = null,
    @SerialName("weight_measured") val weightMeasured: Double? = null
)