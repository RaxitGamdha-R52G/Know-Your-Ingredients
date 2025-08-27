package com.kyi.openfoodfactsapi.models

import UnitHelper
import RecommendedDailyIntakeHelper

class RecommendedDailyIntake(
    val energyKcal: IntakeRecommendation,
    val energyKj: IntakeRecommendation,
    val fat: IntakeRecommendation,
    val saturatedFat: IntakeRecommendation,
    val carbohydrates: IntakeRecommendation,
    val sugars: IntakeRecommendation,
    val proteins: IntakeRecommendation,
    val sodium: IntakeRecommendation,
    val vitaminA: IntakeRecommendation,
    val vitaminD: IntakeRecommendation,
    val vitaminE: IntakeRecommendation,
    val vitaminK: IntakeRecommendation,
    val vitaminC: IntakeRecommendation,
    val vitaminB1: IntakeRecommendation,
    val vitaminB2: IntakeRecommendation,
    val vitaminB3: IntakeRecommendation,
    val vitaminB6: IntakeRecommendation,
    val vitaminB9: IntakeRecommendation,
    val vitaminB12: IntakeRecommendation,
    val biotin: IntakeRecommendation,
    val pantothenicAcid: IntakeRecommendation,
    val potassium: IntakeRecommendation,
    val chloride: IntakeRecommendation,
    val calcium: IntakeRecommendation,
    val phosphorus: IntakeRecommendation,
    val magnesium: IntakeRecommendation,
    val iron: IntakeRecommendation,
    val zinc: IntakeRecommendation,
    val copper: IntakeRecommendation,
    val manganese: IntakeRecommendation,
    val fluoride: IntakeRecommendation,
    val selenium: IntakeRecommendation,
    val chromium: IntakeRecommendation,
    val molybdenum: IntakeRecommendation,
    val iodine: IntakeRecommendation
) {

    companion object {

        fun fromJson(json: Map<String, Any?>): RecommendedDailyIntake = RecommendedDailyIntake(
            IntakeRecommendation(json["energy_kcal"] as Map<String, Any?>),
            IntakeRecommendation(json["energy_kJ"] as Map<String, Any?>),
            IntakeRecommendation(json["fat"] as Map<String, Any?>),
            IntakeRecommendation(json["saturated-fat"] as Map<String, Any?>),
            IntakeRecommendation(json["carbohydrates"] as Map<String, Any?>),
            IntakeRecommendation(json["sugars"] as Map<String, Any?>),
            IntakeRecommendation(json["proteins"] as Map<String, Any?>),
            IntakeRecommendation(json["sodium"] as Map<String, Any?>),
            IntakeRecommendation(json["vitamin-a"] as Map<String, Any?>),
            IntakeRecommendation(json["vitamin-d"] as Map<String, Any?>),
            IntakeRecommendation(json["vitamin-e"] as Map<String, Any?>),
            IntakeRecommendation(json["vitamin-k"] as Map<String, Any?>),
            IntakeRecommendation(json["vitamin-c"] as Map<String, Any?>),
            IntakeRecommendation(json["vitamin-b1"] as Map<String, Any?>),
            IntakeRecommendation(json["vitamin-b2"] as Map<String, Any?>),
            IntakeRecommendation(json["vitamin-b3"] as Map<String, Any?>),
            IntakeRecommendation(json["vitamin-b6"] as Map<String, Any?>),
            IntakeRecommendation(json["vitamin-b9"] as Map<String, Any?>),
            IntakeRecommendation(json["vitamin-b12"] as Map<String, Any?>),
            IntakeRecommendation(json["biotin"] as Map<String, Any?>),
            IntakeRecommendation(json["pantothenic-acid"] as Map<String, Any?>),
            IntakeRecommendation(json["potassium"] as Map<String, Any?>),
            IntakeRecommendation(json["chloride"] as Map<String, Any?>),
            IntakeRecommendation(json["calcium"] as Map<String, Any?>),
            IntakeRecommendation(json["phosphorus"] as Map<String, Any?>),
            IntakeRecommendation(json["magnesium"] as Map<String, Any?>),
            IntakeRecommendation(json["iron"] as Map<String, Any?>),
            IntakeRecommendation(json["zinc"] as Map<String, Any?>),
            IntakeRecommendation(json["copper"] as Map<String, Any?>),
            IntakeRecommendation(json["manganese"] as Map<String, Any?>),
            IntakeRecommendation(json["fluoride"] as Map<String, Any?>),
            IntakeRecommendation(json["selenium"] as Map<String, Any?>),
            IntakeRecommendation(json["chromium"] as Map<String, Any?>),
            IntakeRecommendation(json["molybdenum"] as Map<String, Any?>),
            IntakeRecommendation(json["iodine"] as Map<String, Any?>)
        )

        fun getRecommendedDailyIntakes(): RecommendedDailyIntake =
            fromJson(RecommendedDailyIntakeHelper.getEURecommendationsJson())
    }
}

class IntakeRecommendation(json: Map<String, Any?>) {

    var unit: Unit? = UnitHelper.stringToUnit(json["unit"] as String?)

    var value: Double? = if (json["value"] is Double) json["value"] as Double else (json["value"] as Int).toDouble()
}