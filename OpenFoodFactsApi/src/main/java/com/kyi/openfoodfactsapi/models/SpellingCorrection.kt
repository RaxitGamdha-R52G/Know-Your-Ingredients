package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

class SpellingCorrection(
    var corrected: String? = null,
    var input: String? = null,
    var termCorrections: List<TermCorrections>? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "corrected" to corrected,
        "text" to input,
        "corrections" to termCorrections
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): SpellingCorrection = SpellingCorrection(
            corrected = json["corrected"] as String?,
            input = json["text"] as String?,
            termCorrections = (json["corrections"] as List<*>?)?.map { TermCorrections.fromJson(it as Map<String, Any?>) }
        )
    }
}

class TermCorrections(
    val corrections: List<Correction>? = null,
    val score: Double? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "term_corrections" to corrections,
        "score" to score
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): TermCorrections = TermCorrections(
            corrections = (json["term_corrections"] as List<*>?)?.map { Correction.fromJson(it as Map<String, Any?>) },
            score = (json["score"] as Number?)?.toDouble()
        )
    }
}

class Correction(
    var correction: String? = null,
    var original: String? = null,
    var startOffset: Int? = null,
    var endOffset: Int? = null,
    var isValid: Boolean? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "correction" to correction,
        "original" to original,
        "start_offset" to startOffset,
        "end_offset" to endOffset,
        "is_valid" to isValid
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): Correction = Correction(
            correction = json["correction"] as String?,
            original = json["original"] as String?,
            startOffset = (json["start_offset"] as Number?)?.toInt(),
            endOffset = (json["end_offset"] as Number?)?.toInt(),
            isValid = json["is_valid"] as Boolean?
        )
    }
}