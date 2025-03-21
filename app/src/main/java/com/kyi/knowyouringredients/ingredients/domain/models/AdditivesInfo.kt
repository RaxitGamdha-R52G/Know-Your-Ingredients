package com.kyi.knowyouringredients.ingredients.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdditivesInfo(
    @SerialName("additives_n") val count: Int? = null,
    @SerialName("additives_tags") val tags: List<String> = emptyList()
)