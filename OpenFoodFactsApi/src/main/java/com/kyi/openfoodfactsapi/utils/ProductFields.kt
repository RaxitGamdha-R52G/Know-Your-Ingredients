package com.kyi.openfoodfactsapi.utils

import com.kyi.openfoodfactsapi.sources.OffTagged
enum class ProductField(override val offTag: String, private val inLanguagesProductField: ProductField? = null, val isInLanguages: Boolean = false, val isAllLanguages: Boolean = false) : OffTagged {
    BARCODE("code"),
    PRODUCT_TYPE("product_type"),
    NAME("product_name", inLanguagesProductField = NAME_IN_LANGUAGES),
    NAME_IN_LANGUAGES("product_name_", isInLanguages = true),
    NAME_ALL_LANGUAGES("product_name_languages", inLanguagesProductField = NAME_IN_LANGUAGES, isAllLanguages = true),
    CONSERVATION_CONDITIONS_ALL_LANGUAGES("conservation_conditions_languages", isAllLanguages = true),
    CUSTOMER_SERVICE_ALL_LANGUAGES("customer_service_languages", isAllLanguages = true),
    GENERIC_NAME("generic_name", inLanguagesProductField = GENERIC_NAME_IN_LANGUAGES),
    GENERIC_NAME_IN_LANGUAGES("generic_name_", isInLanguages = true),
    GENERIC_NAME_ALL_LANGUAGES("generic_name_languages", inLanguagesProductField = GENERIC_NAME_IN_LANGUAGES, isAllLanguages = true),
    ABBREVIATED_NAME("abbreviated_product_name", inLanguagesProductField = ABBREVIATED_NAME_IN_LANGUAGES),
    ABBREVIATED_NAME_IN_LANGUAGES("abbreviated_product_name_", isInLanguages = true),
    ABBREVIATED_NAME_ALL_LANGUAGES("abbreviated_product_name_languages", inLanguagesProductField = ABBREVIATED_NAME_IN_LANGUAGES, isAllLanguages = true),
    BRANDS("brands"),
    BRANDS_TAGS("brands_tags", inLanguagesProductField = BRANDS_TAGS_IN_LANGUAGES),
    BRANDS_TAGS_IN_LANGUAGES("brands_tags_", isInLanguages = true),
    COUNTRIES("countries"),
    COUNTRIES_TAGS("countries_tags", inLanguagesProductField = COUNTRIES_TAGS_IN_LANGUAGES),
    COUNTRIES_TAGS_IN_LANGUAGES("countries_tags_", isInLanguages = true),
    LANGUAGE("lang"),
    QUANTITY("quantity"),
    SERVING_SIZE("serving_size"),
    SERVING_QUANTITY("serving_quantity"),
    PACKAGING_QUANTITY("product_quantity"),
    FRONT_IMAGE("image_small_url"),
    SELECTED_IMAGE("selected_images"),
    IMAGE_FRONT_URL("image_front_url"),
    IMAGE_FRONT_SMALL_URL("image_front_small_url"),
    IMAGE_INGREDIENTS_URL("image_ingredients_url"),
    IMAGE_INGREDIENTS_SMALL_URL("image_ingredients_small_url"),
    IMAGE_NUTRITION_URL("image_nutrition_url"),
    IMAGE_NUTRITION_SMALL_URL("image_nutrition_small_url"),
    IMAGE_PACKAGING_URL("image_packaging_url"),
    IMAGE_PACKAGING_SMALL_URL("image_packaging_small_url"),
    IMAGES("images"),
    INGREDIENTS("ingredients"),
    INGREDIENTS_TAGS("ingredients_tags", inLanguagesProductField = INGREDIENTS_TAGS_IN_LANGUAGES),
    INGREDIENTS_TAGS_IN_LANGUAGES("ingredients_tags_", isInLanguages = true),
    IMAGES_FRESHNESS_IN_LANGUAGES("images_to_update_", isInLanguages = true),
    NO_NUTRITION_DATA("no_nutrition_data"),
    NUTRIMENTS("nutriments"),
    ADDITIVES("additives_tags", inLanguagesProductField = ADDITIVES_TAGS_IN_LANGUAGES),
    ADDITIVES_TAGS_IN_LANGUAGES("additives_tags_", isInLanguages = true),
    NUTRIENT_LEVELS("nutrient_levels"),
    INGREDIENTS_TEXT("ingredients_text", inLanguagesProductField = INGREDIENTS_TEXT_IN_LANGUAGES),
    INGREDIENTS_TEXT_IN_LANGUAGES("ingredients_text_", isInLanguages = true),
    INGREDIENTS_TEXT_ALL_LANGUAGES("ingredients_text_languages", inLanguagesProductField = INGREDIENTS_TEXT_IN_LANGUAGES, isAllLanguages = true),
    NUTRIMENT_ENERGY_UNIT("nutriment_energy_unit"),
    NUTRIMENT_DATA_PER("nutrition_data_per"),
    NUTRITION_DATA("nutrition_data"),
    NUTRISCORE("nutrition_grade_fr"),
    COMPARED_TO_CATEGORY("compared_to_category"),
    CATEGORIES("categories"),
    CATEGORIES_TAGS("categories_tags", inLanguagesProductField = CATEGORIES_TAGS_IN_LANGUAGES),
    CATEGORIES_TAGS_IN_LANGUAGES("categories_tags_", isInLanguages = true),
    LABELS("labels"),
    LABELS_TAGS("labels_tags", inLanguagesProductField = LABELS_TAGS_IN_LANGUAGES),
    LABELS_TAGS_IN_LANGUAGES("labels_tags_", isInLanguages = true),
    PACKAGING("packaging"),
    PACKAGINGS("packagings"),
    PACKAGINGS_COMPLETE("packagings_complete"),
    PACKAGING_TAGS("packaging_tags"),
    PACKAGING_TEXT_IN_LANGUAGES("packaging_text_", isInLanguages = true),
    PACKAGING_TEXT_ALL_LANGUAGES("packaging_text_languages", inLanguagesProductField = PACKAGING_TEXT_IN_LANGUAGES, isAllLanguages = true),
    MISC_TAGS("misc_tags", inLanguagesProductField = MISC_TAGS_IN_LANGUAGES),
    MISC_TAGS_IN_LANGUAGES("misc_tags_", isInLanguages = true),
    STATES_TAGS("states_tags", inLanguagesProductField = STATES_TAGS_IN_LANGUAGES),
    STATES_TAGS_IN_LANGUAGES("states_tags_", isInLanguages = true),
    TRACES_TAGS("traces_tags", inLanguagesProductField = TRACES_TAGS_IN_LANGUAGES),
    TRACES_TAGS_IN_LANGUAGES("traces_tags_", isInLanguages = true),
    TRACES("traces"),
    STORES_TAGS("stores_tags", inLanguagesProductField = STORES_TAGS_IN_LANGUAGES),
    STORES_TAGS_IN_LANGUAGES("stores_tags_", isInLanguages = true),
    STORES("stores"),
    INGREDIENTS_ANALYSIS_TAGS("ingredients_analysis_tags", inLanguagesProductField = INGREDIENTS_ANALYSIS_TAGS_IN_LANGUAGES),
    INGREDIENTS_ANALYSIS_TAGS_IN_LANGUAGES("ingredients_analysis_tags_", isInLanguages = true),
    ALLERGENS("allergens_tags", inLanguagesProductField = ALLERGENS_TAGS_IN_LANGUAGES),
    ALLERGENS_TAGS_IN_LANGUAGES("allergens_tags_", isInLanguages = true),
    ATTRIBUTE_GROUPS("attribute_groups"),
    LAST_MODIFIED("last_modified_t"),
    LAST_MODIFIER("last_modified_by"),
    LAST_CHECKED("last_checked_t"),
    LAST_CHECKER("last_checker"),
    LAST_EDITOR("last_editor"),
    LAST_IMAGE("last_image_t"),
    ENTRY_DATES("entry_dates_tags"),
    LAST_CHECK_DATES("last_check_dates_tags"),
    LAST_EDIT_DATES("last_edit_dates_tags"),
    LAST_IMAGE_DATES("last_image_dates_tags"),
    CREATED("created_t"),
    CREATOR("creator"),
    EDITORS("editors_tags"),
    ECOSCORE_GRADE("ecoscore_grade"),
    ECOSCORE_SCORE("ecoscore_score"),
    ECOSCORE_DATA("ecoscore_data"),
    KNOWLEDGE_PANELS("knowledge_panels"),
    EMB_CODES("emb_codes"),
    MANUFACTURING_PLACES("manufacturing_places"),
    ORIGINS("origins"),
    NOVA_GROUP("nova_group"),
    WEBSITE("link"),
    EXPIRATION_DATE("expiration_date"),
    OBSOLETE("obsolete"),
    OWNER_FIELDS("owner_fields"),
    OWNER("owner"),
    DATA_QUALITY_TAGS("data_quality_tags"),
    DATA_QUALITY_BUGS_TAGS("data_quality_bugs_tags"),
    DATA_QUALITY_ERRORS_TAGS("data_quality_errors_tags"),
    DATA_QUALITY_INFO_TAGS("data_quality_info_tags"),
    DATA_QUALITY_WARNINGS_TAGS("data_quality_warnings_tags"),
    RAW("raw"),
    ALL("");

    val inLanguages: ProductField? get() = if (isInLanguages) this else inLanguagesProductField

    companion object {

        private var inLanguagesList = listOf<ProductField>()

        private var allLanguagesList = listOf<ProductField>()

        fun getInLanguagesList(): List<ProductField> {
            if (inLanguagesList.isEmpty()) {
                for (productField in entries) {
                    if (productField.isInLanguages) inLanguagesList.add(productField)
                }
            }
            return inLanguagesList
        }

        fun getAllLanguagesList(): List<ProductField> {
            if (allLanguagesList.isEmpty()) {
                for (productField in entries) {
                    if (productField.isAllLanguages) allLanguagesList.add(productField)
                }
            }
            return allLanguagesList
        }
    }

    fun convertFieldsToStrings(fields: List<ProductField>, languages: List<OpenFoodFactsLanguage>): List<String> {
        val fieldsStrings = mutableListOf<String>()
        for (field in fields) {
            if (field.isInLanguages) {
                if (languages.isEmpty()) throw IllegalArgumentException("Cannot request in-lang field $field without language")
                for (language in languages) {
                    fieldsStrings.add("${field.offTag}${language.offTag}")
                }
            } else {
                fieldsStrings.add(field.offTag)
            }
        }
        return fieldsStrings
    }
}