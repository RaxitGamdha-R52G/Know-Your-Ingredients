package com.kyi.knowyouringredients.ingredients.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Packaging(
    @SerialName("material") val material: String?, // Nullable since some entries lack it
    @SerialName("number_of_units") val numberOfUnits: String? = "1",
    @SerialName("quantity_per_unit_value") val quantityValue: String? = "0",
    @SerialName("quantity_per_unit_unit") val quantityUnit: String? = "g",
    @SerialName("shape") val shape: String? = null,
    @SerialName("recycling") val recycling: String? = null,
    @SerialName("weight_measured") val weightMeasured: Double? = null
)