package com.kyi.openfoodfactsapi.models.taxonomy

import LanguageHelper
import OpenFoodFactsLanguage
import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.sources.OffTagged

enum class TaxonomyAdditiveField(override val offTag: String) : OffTagged {
    ALL("all"),
    ADDITIVES_CLASSES("additives_classes"),
    CARBON_FOOTPRINT_FR_FOODGES_INGREDIENT("carbon_footprint_fr_foodges_ingredient"),
    CARBON_FOOTPRINT_FR_FOODGES_VALUE("carbon_footprint_fr_foodges_value"),
    COLOUR_INDEX("colour_index"),
    COMMENT("comment"),
    DEFAULT_ADDITIVE_CLASS("default_additive_class"),
    DESCRIPTION("description"),
    E_NUMBER("e_number"),
    EFSA("efsa"),
    EFSA_EVALUATION("efsa_evaluation"),
    EFSA_EVALUATION_ADI("efsa_evaluation_adi"),
    EFSA_EVALUATION_ADI_ESTABLISHED("efsa_evaluation_adi_established"),
    EFSA_EVALUATION_DATE("efsa_evaluation_date"),
    EFSA_EVALUATION_EXPOSURE_95TH_GREATER_THAN_ADI("efsa_evaluation_exposure_95th_greater_than_adi"),
    EFSA_EVALUATION_EXPOSURE_95TH_GREATER_THAN_NOAEL("efsa_evaluation_exposure_95th_greater_than_noael"),
    EFSA_EVALUATION_EXPOSURE_MEAN_GREATER_THAN_ADI("efsa_evaluation_exposure_mean_greater_than_adi"),
    EFSA_EVALUATION_EXPOSURE_MEAN_GREATER_THAN_NOAEL("efsa_evaluation_exposure_mean_greater_than_noael"),
    EFSA_EVALUATION_OVEREXPOSURE_RISK("efsa_evaluation_overexposure_risk"),
    EFSA_EVALUATION_SAFETY_ASSESSED("efsa_evaluation_safety_assessed"),
    EFSA_EVALUATION_URL("efsa_evaluation_url"),
    FROM_PALM_OIL("from_palm_oil"),
    MANDATORY_ADDITIVE_CLASS("mandatory_additive_class"),
    NAME("name"),
    ORGANIC_EU("organic_eu"),
    VEGAN("vegan"),
    VEGETARIAN("vegetarian"),
    WIKIDATA("wikidata")
}

class TaxonomyAdditive(
    val additivesClasses: Map<OpenFoodFactsLanguage, String>? = null,
    val carbonFootprintFrFoodgesIngredient: Map<OpenFoodFactsLanguage, String>? = null,
    val carbonFootprintFrFoodgesValue: Map<OpenFoodFactsLanguage, String>? = null,
    val children: List<String>? = null,
    val colourIndex: Map<OpenFoodFactsLanguage, String>? = null,
    val comment: Map<OpenFoodFactsLanguage, String>? = null,
    val defaultAdditiveClass: Map<OpenFoodFactsLanguage, String>? = null,
    val description: Map<OpenFoodFactsLanguage, String>? = null,
    val eNumber: Map<OpenFoodFactsLanguage, String>? = null,
    val efsa: Map<OpenFoodFactsLanguage, String>? = null,
    val efsaEvaluation: Map<OpenFoodFactsLanguage, String>? = null,
    val efsaEvaluationAdi: Map<OpenFoodFactsLanguage, String>? = null,
    val efsaEvaluationAdiEstablished: Map<OpenFoodFactsLanguage, String>? = null,
    val efsaEvaluationDate: Map<OpenFoodFactsLanguage, String>? = null,
    val efsaEvaluationExposure95thGreaterThanAdi: Map<OpenFoodFactsLanguage, String>? = null,
    val efsaEvaluationExposure95thGreaterThanNoael: Map<OpenFoodFactsLanguage, String>? = null,
    val efsaEvaluationExposureMeanGreaterThanAdi: Map<OpenFoodFactsLanguage, String>? = null,
    val efsaEvaluationExposureMeanGreaterThanNoael: Map<OpenFoodFactsLanguage, String>? = null,
    val efsaEvaluationOverexposureRisk: Map<OpenFoodFactsLanguage, String>? = null,
    val efsaEvaluationSafetyAssessed: Map<OpenFoodFactsLanguage, String>? = null,
    val efsaEvaluationUrl: Map<OpenFoodFactsLanguage, String>? = null,
    val fromPalmOil: Map<OpenFoodFactsLanguage, String>? = null,
    val mandatoryAdditiveClass: Map<OpenFoodFactsLanguage, String>? = null,
    val name: Map<OpenFoodFactsLanguage, String>? = null,
    val organicEu: Map<OpenFoodFactsLanguage, String>? = null,
    val parents: List<String>? = null,
    val vegan: Map<OpenFoodFactsLanguage, String>? = null,
    val vegetarian: Map<OpenFoodFactsLanguage, String>? = null,
    val wikidata: Map<OpenFoodFactsLanguage, String>? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "additives_classes" to LanguageHelper.toJsonStringMap(additivesClasses),
        "carbon_footprint_fr_foodges_ingredient" to LanguageHelper.toJsonStringMap(carbonFootprintFrFoodgesIngredient),
        "carbon_footprint_fr_foodges_value" to LanguageHelper.toJsonStringMap(carbonFootprintFrFoodgesValue),
        "children" to children,
        "colour_index" to LanguageHelper.toJsonStringMap(colourIndex),
        "comment" to LanguageHelper.toJsonStringMap(comment),
        "default_additive_class" to LanguageHelper.toJsonStringMap(defaultAdditiveClass),
        "description" to LanguageHelper.toJsonStringMap(description),
        "e_number" to LanguageHelper.toJsonStringMap(eNumber),
        "efsa" to LanguageHelper.toJsonStringMap(efsa),
        "efsa_evaluation" to LanguageHelper.toJsonStringMap(efsaEvaluation),
        "efsa_evaluation_adi" to LanguageHelper.toJsonStringMap(efsaEvaluationAdi),
        "efsa_evaluation_adi_established" to LanguageHelper.toJsonStringMap(efsaEvaluationAdiEstablished),
        "efsa_evaluation_date" to LanguageHelper.toJsonStringMap(efsaEvaluationDate),
        "efsa_evaluation_exposure_95th_greater_than_adi" to LanguageHelper.toJsonStringMap(efsaEvaluationExposure95thGreaterThanAdi),
        "efsa_evaluation_exposure_95th_greater_than_noael" to LanguageHelper.toJsonStringMap(efsaEvaluationExposure95thGreaterThanNoael),
        "efsa_evaluation_exposure_mean_greater_than_adi" to LanguageHelper.toJsonStringMap(efsaEvaluationExposureMeanGreaterThanAdi),
        "efsa_evaluation_exposure_mean_greater_than_noael" to LanguageHelper.toJsonStringMap(efsaEvaluationExposureMeanGreaterThanNoael),
        "efsa_evaluation_overexposure_risk" to LanguageHelper.toJsonStringMap(efsaEvaluationOverexposureRisk),
        "efsa_evaluation_safety_assessed" to LanguageHelper.toJsonStringMap(efsaEvaluationSafetyAssessed),
        "efsa_evaluation_url" to LanguageHelper.toJsonStringMap(efsaEvaluationUrl),
        "from_palm_oil" to LanguageHelper.toJsonStringMap(fromPalmOil),
        "mandatory_additive_class" to LanguageHelper.toJsonStringMap(mandatoryAdditiveClass),
        "name" to LanguageHelper.toJsonStringMap(name),
        "organic_eu" to LanguageHelper.toJsonStringMap(organicEu),
        "parents" to parents,
        "vegan" to LanguageHelper.toJsonStringMap(vegan),
        "vegetarian" to LanguageHelper.toJsonStringMap(vegetarian),
        "wikidata" to LanguageHelper.toJsonStringMap(wikidata)
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): TaxonomyAdditive = TaxonomyAdditive(
            additivesClasses = LanguageHelper.fromJsonStringMap(json["additives_classes"]),
            carbonFootprintFrFoodgesIngredient = LanguageHelper.fromJsonStringMap(json["carbon_footprint_fr_foodges_ingredient"]),
            carbonFootprintFrFoodgesValue = LanguageHelper.fromJsonStringMap(json["carbon_footprint_fr_foodges_value"]),
            children = (json["children"] as List<*>?)?.map { it as String },
            colourIndex = LanguageHelper.fromJsonStringMap(json["colour_index"]),
            comment = LanguageHelper.fromJsonStringMap(json["comment"]),
            defaultAdditiveClass = LanguageHelper.fromJsonStringMap(json["default_additive_class"]),
            description = LanguageHelper.fromJsonStringMap(json["description"]),
            eNumber = LanguageHelper.fromJsonStringMap(json["e_number"]),
            efsa = LanguageHelper.fromJsonStringMap(json["efsa"]),
            efsaEvaluation = LanguageHelper.fromJsonStringMap(json["efsa_evaluation"]),
            efsaEvaluationAdi = LanguageHelper.fromJsonStringMap(json["efsa_evaluation_adi"]),
            efsaEvaluationAdiEstablished = LanguageHelper.fromJsonStringMap(json["efsa_evaluation_adi_established"]),
            efsaEvaluationDate = LanguageHelper.fromJsonStringMap(json["efsa_evaluation_date"]),
            efsaEvaluationExposure95thGreaterThanAdi = LanguageHelper.fromJsonStringMap(json["efsa_evaluation_exposure_95th_greater_than_adi"]),
            efsaEvaluationExposure95thGreaterThanNoael = LanguageHelper.fromJsonStringMap(json["efsa_evaluation_exposure_95th_greater_than_noael"]),
            efsaEvaluationExposureMeanGreaterThanAdi = LanguageHelper.fromJsonStringMap(json["efsa_evaluation_exposure_mean_greater_than_adi"]),
            efsaEvaluationExposureMeanGreaterThanNoael = LanguageHelper.fromJsonStringMap(json["efsa_evaluation_exposure_mean_greater_than_noael"]),
            efsaEvaluationOverexposureRisk = LanguageHelper.fromJsonStringMap(json["efsa_evaluation_overexposure_risk"]),
            efsaEvaluationSafetyAssessed = LanguageHelper.fromJsonStringMap(json["efsa_evaluation_safety_assessed"]),
            efsaEvaluationUrl = LanguageHelper.fromJsonStringMap(json["efsa_evaluation_url"]),
            fromPalmOil = LanguageHelper.fromJsonStringMap(json["from_palm_oil"]),
            mandatoryAdditiveClass = LanguageHelper.fromJsonStringMap(json["mandatory_additive_class"]),
            name = LanguageHelper.fromJsonStringMap(json["name"]),
            organicEu = LanguageHelper.fromJsonStringMap(json["organic_eu"]),
            parents = (json["parents"] as List<*>?)?.map { it as String },
            vegan = LanguageHelper.fromJsonStringMap(json["vegan"]),
            vegetarian = LanguageHelper.fromJsonStringMap(json["vegetarian"]),
            wikidata = LanguageHelper.fromJsonStringMap(json["wikidata"])
        )
    }

    override fun toString(): String = toJson().toString()
}