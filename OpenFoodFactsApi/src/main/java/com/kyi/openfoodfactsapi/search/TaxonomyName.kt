package com.kyi.openfoodfactsapi.search

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class TaxonomyName(override val offTag: String) : OffTagged {
    CATEGORY("category"),
    LABEL("label"),
    ADDITIVE("additive"),
    ALLERGEN("allergen"),
    AMINO_ACID("amino_acid"),
    COUNTRY("country"),
    DATA_QUALITY("data_quality"),
    FOOD_GROUP("food_group"),
    IMPROVEMENT("improvement"),
    INGREDIENT("ingredient"),
    INGREDIENTS_ANALYSIS("ingredients_analysis"),
    INGREDIENT_PROCESSING("ingredients_processing"),
    LANGUAGE("language"),
    MINERAL("mineral"),
    MISC("misc"),
    NOVA_GROUP("nova_group"),
    NUCLEOTIDE("nucleotide"),
    NUTRIENT("nutrient"),
    ORIGIN("origin"),
    OTHER_NUTRITIONAL_SUBSTANCE("other_nutritional_substance"),
    PACKAGING_MATERIAL("packaging_material"),
    PACKAGING_RECYCLING("packaging_recycling"),
    PACKAGING_SHAPE("packaging_shape"),
    PERIODS_AFTER_OPENING("periods_after_opening"),
    PRESERVATION("preservation"),
    STATE("state"),
    VITAMIN("vitamin"),
    BRAND("brand");

    companion object {

        fun fromOffTag(offTag: String?): TaxonomyName? = entries.firstOrNull { it.offTag == offTag }
    }
}