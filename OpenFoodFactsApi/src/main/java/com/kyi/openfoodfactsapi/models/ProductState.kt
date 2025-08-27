package com.kyi.openfoodfactsapi.models


enum class ProductState(val completedTag: String, val toBeCompletedTag: String) {
    CHECKED("en:checked", "en:to-be-checked"),
    COMPLETED("en:complete", "en:to-be-completed"),
    NUTRITION_FACTS_COMPLETED("en:nutrition-facts-completed", "en:nutrition-facts-to-be-completed"),
    INGREDIENTS_COMPLETED("en:ingredients-completed", "en:ingredients-to-be-completed"),
    EXPIRATION_DATE_COMPLETED("en:expiration-date-completed", "en:expiration-date-to-be-completed"),
    PACKAGING_CODE_COMPLETED("en:packaging-code-completed", "en:packaging-code-to-be-completed"),
    CHARACTERISTICS_COMPLETED("en:characteristics-completed", "en:characteristics-to-be-completed"),
    ORIGINS_COMPLETED("en:origins-completed", "en:origins-to-be-completed"),
    CATEGORIES_COMPLETED("en:categories-completed", "en:categories-to-be-completed"),
    BRANDS_COMPLETED("en:brands-completed", "en:brands-to-be-completed"),
    PACKAGING_COMPLETED("en:packaging-completed", "en:packaging-to-be-completed"),
    QUANTITY_COMPLETED("en:quantity-completed", "en:quantity-to-be-completed"),
    PRODUCT_NAME_COMPLETED("en:product-name-completed", "en:product-name-to-be-completed"),
    PHOTOS_VALIDATED("en:photos-validated", "en:photos-to-be-validated"),
    PACKAGING_PHOTO_SELECTED("en:packaging-photo-selected", "en:packaging-photo-to-be-selected"),
    NUTRITION_PHOTO_SELECTED("en:nutrition-photo-selected", "en:nutrition-photo-to-be-selected"),
    INGREDIENTS_PHOTO_SELECTED("en:ingredients-photo-selected", "en:ingredients-photo-to-be-selected"),
    FRONT_PHOTO_SELECTED("en:front-photo-selected", "en:front-photo-to-be-selected"),
    PHOTOS_UPLOADED("en:photos-uploaded", "en:photos-to-be-uploaded");

    companion object {

        fun getCompleted(tag: String): ProductState? = entries.firstOrNull { it.completedTag == tag }

        fun getToBeCompleted(tag: String): ProductState? = entries.firstOrNull { it.toBeCompletedTag == tag }
    }
}