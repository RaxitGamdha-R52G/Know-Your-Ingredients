package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject


class OcrPackagingResult(
    val status: Int? = null,
    val textFromImageOrig: String? = null,
    val textFromImage: String? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "status" to status,
        "packaging_text_from_image_orig" to textFromImageOrig,
        "packaging_text_from_image" to textFromImage
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): OcrPackagingResult = OcrPackagingResult(
            status = (json["status"] as Number?)?.toInt(),
            textFromImageOrig = json["packaging_text_from_image_orig"] as String?,
            textFromImage = json["packaging_text_from_image"] as String?
        )
    }
}