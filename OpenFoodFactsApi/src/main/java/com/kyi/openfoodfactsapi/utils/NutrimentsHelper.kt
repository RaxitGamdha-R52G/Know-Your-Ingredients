package com.kyi.openfoodfactsapi.utils

import com.kyi.openfoodfactsapi.models.Nutrient
import com.kyi.openfoodfactsapi.models.Nutriments
import com.kyi.openfoodfactsapi.models.PerSize

object NutrimentsHelper {

    private const val KCAL_TO_KJ_FACTOR = 4.1868

    fun fromKCalToKJ(kCal: Double): Double = kCal * KCAL_TO_KJ_FACTOR

    fun fromKJtoKCal(kJ: Double): Double = kJ / KCAL_TO_KJ_FACTOR

    fun calculateEnergy(nutriments: Nutriments, perSize: PerSize = PerSize.ONE_HUNDRED_GRAMS): Double? {
        val fat = nutriments.getValue(Nutrient.FAT, perSize)
        val carbs = nutriments.getValue(Nutrient.CARBOHYDRATES, perSize)
        val proteins = nutriments.getValue(Nutrient.PROTEINS, perSize)
        val fiber = nutriments.getValue(Nutrient.FIBER, perSize)

        if (fat == null || carbs == null || proteins == null || fiber == null) return null

        return (fat * 37 + carbs * 17 + proteins * 17 + fiber * 8)
    }

    fun checkEnergyCoherence(nutriments: Nutriments, marginPercentage: Double, perSize: PerSize = PerSize.ONE_HUNDRED_GRAMS): Boolean {
        val statedEnergy = nutriments.getComputedKJ(perSize) ?: return false

        val lowLimit = statedEnergy - (statedEnergy * (marginPercentage / 100.0))
        val highLimit = statedEnergy + (statedEnergy * (marginPercentage / 100.0))

        val calculatedEnergy = calculateEnergy(nutriments)!!

        return (calculatedEnergy >= lowLimit && calculatedEnergy <= highLimit)
    }
}