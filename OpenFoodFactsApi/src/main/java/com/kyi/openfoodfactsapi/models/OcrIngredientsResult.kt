package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

class OcrIngredientsResult(
    val status: Int? = null,
    val ingredientsTextFromImageOrig: String? = null,
    val ingredientsTextFromImage: String? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "status" to status,
        "ingredients_text_from_image_orig" to ingredientsTextFromImageOrig,
        "ingredients_text_from_image" to ingredientsTextFromImage
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): OcrIngredientsResult = OcrIngredientsResult(
            status = (json["status"] as Number?)?.toInt(),
            ingredientsTextFromImageOrig = json["ingredients_text_from_image_orig"] as String?,
            ingredientsTextFromImage = json["ingredients_text_from_image"] as String?
        )
    }
}