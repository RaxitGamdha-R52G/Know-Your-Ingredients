package com.kyi.knowyouringredients.ingredients.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Packaging(
    @SerialName("material") val material: String? = null,
    @SerialName("shape") val shape: String? = null,
    @SerialName("recycling") val recycling: String? = null,
    @SerialName("number_of_units") val numberOfUnits: String? = "1"
)