package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.models.LabelsInfo

data class LabelsInfoUI(
    val tags: List<String>
) {
    companion object {
        fun fromDomain(labelsInfo: LabelsInfo?): LabelsInfoUI {
            return LabelsInfoUI(
                tags = labelsInfo?.tags?.map { it.replace(Regex("^[a-z]{2}:"), "") } ?: emptyList()
            )
        }
    }
}