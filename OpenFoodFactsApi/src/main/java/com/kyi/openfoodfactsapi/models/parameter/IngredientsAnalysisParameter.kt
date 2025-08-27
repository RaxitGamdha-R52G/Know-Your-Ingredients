package com.kyi.openfoodfactsapi.models.parameter

import com.kyi.openfoodfactsapi.sources.Parameter
import com.kyi.openfoodfactsapi.models.PalmOilFreeStatus
import com.kyi.openfoodfactsapi.models.VeganStatus
import com.kyi.openfoodfactsapi.models.VegetarianStatus

/**
 * Ingredients Analysis search API parameter.
 */
class IngredientsAnalysisParameter(
    val veganStatus: VeganStatus? = null,
    val vegetarianStatus: VegetarianStatus? = null,
    val palmOilFreeStatus: PalmOilFreeStatus? = null
) : Parameter() {

    override fun getName(): String = "ingredients_analysis_tags"

    override fun getValue(): String {
        val result = mutableListOf<String>()
        if (veganStatus != null) {
            result.add(veganStatus.offTag)
        }
        if (vegetarianStatus != null) {
            result.add(vegetarianStatus.offTag)
        }
        if (palmOilFreeStatus != null) {
            result.add(palmOilFreeStatus.offTag)
        }
        if (result.isEmpty()) {
            return ""
        }
        return result.joinToString(",")
    }
}