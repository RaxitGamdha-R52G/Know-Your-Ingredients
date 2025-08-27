package com.kyi.openfoodfactsapi.utils

object RecommendedDailyIntakeHelper {

    private val euRecommendations = mapOf(
        "energy_kcal" to mapOf("unit" to "kcal", "value" to 2000),
        "energy_kJ" to mapOf("unit" to "kJ", "value" to 8400),
        "fat" to mapOf("unit" to "g", "value" to 70),
        "saturated-fat" to mapOf("unit" to "g", "value" to 20),
        "carbohydrates" to mapOf("unit" to "g", "value" to 260),
        "sugars" to mapOf("unit" to "g", "value" to 90),
        "proteins" to mapOf("unit" to "g", "value" to 50),
        "sodium" to mapOf("unit" to "g", "value" to 6),
        "vitamin-a" to mapOf("unit" to "μg", "value" to 800),
        "vitamin-d" to mapOf("unit" to "μg", "value" to 5),
        "vitamin-e" to mapOf("unit" to "mg", "value" to 12),
        "vitamin-k" to mapOf("unit" to "μg", "value" to 75),
        "vitamin-c" to mapOf("unit" to "mg", "value" to 80),
        "vitamin-b1" to mapOf("unit" to "mg", "value" to 1.1),
        "vitamin-b2" to mapOf("unit" to "mg", "value" to 1.4),
        "vitamin-b3" to mapOf("unit" to "mg", "value" to 16),
        "vitamin-b6" to mapOf("unit" to "mg", "value" to 1.4),
        "vitamin-b9" to mapOf("unit" to "μg", "value" to 200),
        "vitamin-b12" to mapOf("unit" to "μg", "value" to 2.5),
        "biotin" to mapOf("unit" to "μg", "value" to 50),
        "pantothenic-acid" to mapOf("unit" to "mg", "value" to 6),
        "potassium" to mapOf("unit" to "mg", "value" to 2000),
        "chloride" to mapOf("unit" to "mg", "value" to 800),
        "calcium" to mapOf("unit" to "mg", "value" to 800),
        "phosphorus" to mapOf("unit" to "mg", "value" to 700),
        "magnesium" to mapOf("unit" to "mg", "value" to 375),
        "iron" to mapOf("unit" to "mg", "value" to 14),
        "zinc" to mapOf("unit" to "mg", "value" to 10),
        "copper" to mapOf("unit" to "mg", "value" to 1),
        "manganese" to mapOf("unit" to "mg", "value" to 2),
        "fluoride" to mapOf("unit" to "mg", "value" to 3.5),
        "selenium" to mapOf("unit" to "μg", "value" to 55),
        "chromium" to mapOf("unit" to "μg", "value" to 40),
        "molybdenum" to mapOf("unit" to "μg", "value" to 50),
        "iodine" to mapOf("unit" to "μg", "value" to 150)
    )

    fun getEURecommendationsJson(): Map<String, Any> = euRecommendations
}