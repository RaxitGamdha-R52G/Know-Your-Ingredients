package com.kyi.openfoodfactsapi.models

class IngredientsAnalysisTags(data: List<Any?>) {

    var veganStatus: VeganStatus? = getVeganStatus(data)
    var vegetarianStatus: VegetarianStatus? = getVegetarianStatus(data)
    var palmOilFreeStatus: PalmOilFreeStatus? = getPalmOilFreeStatus(data)

    companion object {

        private fun getVeganStatus(data: List<Any?>): VeganStatus? {
            for (status in VeganStatus.entries) {
                if (data.contains(status.offTag)) {
                    return status
                }
            }
            return null
        }

        private fun getVegetarianStatus(data: List<Any?>): VegetarianStatus? {
            for (status in VegetarianStatus.entries) {
                if (data.contains(status.offTag)) {
                    return status
                }
            }
            return null
        }

        private fun getPalmOilFreeStatus(data: List<Any?>): PalmOilFreeStatus? {
            for (status in PalmOilFreeStatus.entries) {
                if (data.contains(status.offTag)) {
                    return status
                }
            }
            return null
        }

        fun fromJson(data: List<Any?>?): IngredientsAnalysisTags? =
            if (data != null) IngredientsAnalysisTags(data) else null

        fun toJson(ingredientsAnalysisTags: IngredientsAnalysisTags?): List<String> {
            val result = mutableListOf<String>()

            if (ingredientsAnalysisTags == null) {
                return result
            }

            ingredientsAnalysisTags.veganStatus?.let { result.add(it.offTag) }
            ingredientsAnalysisTags.vegetarianStatus?.let { result.add(it.offTag) }
            ingredientsAnalysisTags.palmOilFreeStatus?.let { result.add(it.offTag) }

            return result
        }
    }
}