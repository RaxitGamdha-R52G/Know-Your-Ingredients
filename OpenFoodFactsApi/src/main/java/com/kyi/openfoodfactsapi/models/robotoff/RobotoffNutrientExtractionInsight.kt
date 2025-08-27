package com.kyi.openfoodfactsapi.models.robotoff

import JsonHelper
import com.kyi.openfoodfactsapi.sources.JsonObject

class RobotoffNutrientExtractionInsight : JsonObject() {

    var insightId: String? = null
    var barcode: String? = null
    var data: RobotoffNutrientDataWrapper? = null
    var timestamp: DateTime? = null
    var completedAt: DateTime? = null
    var annotation: Int? = null
    var annotatedResult: Int? = null
    var nVotes: Int? = null
    var username: String? = null
    var brands: List<String>? = null
    var processAfter: String? = null
    var valueTag: String? = null
    var value: String? = null
    var sourceImage: String? = null
    var automaticProcessing: Boolean? = null
    var serverType: String? = null
    var uniqueScansN: Int? = null
    var reservedBarcode: Boolean? = null
    var predictor: String? = null
    var predictorVersion: String? = null
    var campaign: List<String>? = null
    var confidence: Double? = null

    companion object {

        fun fromJson(json: Map<String, Any?>): RobotoffNutrientExtractionInsight =
            RobotoffNutrientExtractionInsight().apply {
                insightId = json["id"] as String?
                barcode = json["barcode"] as String?
                data =
                    json["data"]?.let { RobotoffNutrientDataWrapper.fromJson(it as Map<String, Any?>) }
                timestamp = JsonHelper.nullableStringTimestampToDate(json["timestamp"])
                completedAt = JsonHelper.nullableStringTimestampToDate(json["completed_at"])
                annotation = (json["annotation"] as Number?)?.toInt()
                annotatedResult = (json["annotated_result"] as Number?)?.toInt()
                nVotes = (json["n_votes"] as Number?)?.toInt()
                username = json["username"] as String?
                brands = (json["brands"] as List<*>?)?.map { it as String }
                processAfter = json["process_after"] as String?
                valueTag = json["value_tag"] as String?
                value = json["value"] as String?
                sourceImage = json["source_image"] as String?
                automaticProcessing = json["automatic_processing"] as Boolean?
                serverType = json["server_type"] as String?
                uniqueScansN = (json["unique_scans_n"] as Number?)?.toInt()
                reservedBarcode = json["reserved_barcode"] as Boolean?
                predictor = json["predictor"] as String?
                predictorVersion = json["predictor_version"] as String?
                campaign = (json["campaign"] as List<*>?)?.map { it as String }
                confidence = (json["confidence"] as Number?)?.toDouble()
            }
    }

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "id" to insightId,
        "barcode" to barcode,
        "data" to data,
        "timestamp" to timestamp?.toIso8601String(),
        "completed_at" to completedAt?.toIso8601String(),
        "annotation" to annotation,
        "annotated_result" to annotatedResult,
        "n_votes" to nVotes,
        "username" to username,
        "brands" to brands,
        "process_after" to processAfter,
        "value_tag" to valueTag,
        "value" to value,
        "source_image" to sourceImage,
        "automatic_processing" to automaticProcessing,
        "server_type" to serverType,
        "unique_scans_n" to uniqueScansN,
        "reserved_barcode" to reservedBarcode,
        "predictor" to predictor,
        "predictor_version" to predictorVersion,
        "campaign" to campaign,
        "confidence" to confidence
    )
}