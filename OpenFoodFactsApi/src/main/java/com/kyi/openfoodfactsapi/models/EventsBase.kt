package com.kyi.openfoodfactsapi.models

import JsonHelper
import com.kyi.openfoodfactsapi.sources.JsonObject

class EventsBase(
    val eventType: String,
    val timestamp: java.util.Date? = null,
    val userId: String? = null,
    val barcode: String? = null,
    val points: Int? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "event_type" to eventType,
        "timestamp" to timestamp?.toIso8601String(),
        "user_id" to userId,
        "barcode" to barcode,
        "points" to points
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): EventsBase = EventsBase(
            eventType = json["event_type"] as String,
            timestamp = JsonHelper.nullableStringTimestampToDate(json["timestamp"]),
            userId = json["user_id"] as String?,
            barcode = json["barcode"] as String?,
            points = (json["points"] as Number?)?.toInt()
        )
    }

    override fun toString(): String = "EventsBase(eventType: $eventType" +
            "${if (timestamp == null) "" else ", timestamp: $timestamp"}" +
            "${if (userId == null) "" else ", userId: $userId"}" +
            "${if (barcode == null) "" else ", barcode: $barcode"}" +
            "${if (points == null) "" else ", points: $points"}" +
            ")"
}