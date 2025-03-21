package com.kyi.knowyouringredients.ingredients.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LabelsInfo(
    @SerialName("labels_tags") val tags: List<String> = emptyList()
)