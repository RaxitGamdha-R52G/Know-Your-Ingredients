package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.utils.JsonHelper

class Location(
    var osmId: Int? = null,
    var locationType: LocationType,
    var type: LocationOSMType? = null,
    var priceCount: Int? = null,
    var userCount: Int? = null,
    var productCount: Int? = null,
    var proofCount: Int? = null,
    var locationId: Int,
    var name: String? = null,
    var displayName: String? = null,
    var postcode: String? = null,
    var city: String? = null,
    var country: String? = null,
    var countryCode: String? = null,
    var osmKey: String? = null,
    var osmValue: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var websiteUrl: String? = null,
    var created: DateTime,
    var updated: DateTime? = null
) : JsonObject() {

    companion object {

        fun fromJson(json: Map<String, Any?>): Location = Location(
            osmId = json["osm_id"] as Int?,
            locationType = LocationType.fromOffTag(json["type"] as String)!!,
            type = LocationOSMType.fromOffTag(json["osm_type"] as String?),
            priceCount = json["price_count"] as Int?,
            userCount = json["user_count"] as Int?,
            productCount = json["product_count"] as Int?,
            proofCount = json["proof_count"] as Int?,
            locationId = json["id"] as Int,
            name = json["osm_name"] as String?,
            displayName = json["osm_display_name"] as String?,
            postcode = json["osm_address_postcode"] as String?,
            city = json["osm_address_city"] as String?,
            country = json["osm_address_country"] as String?,
            countryCode = json["osm_address_country_code"] as String?,
            osmKey = json["osm_tag_key"] as String?,
            osmValue = json["osm_tag_value"] as String?,
            latitude = (json["osm_lat"] as Double?),
            longitude = (json["osm_lon"] as Double?),
            websiteUrl = json["website_url"] as String?,
            created = JsonHelper.stringTimestampToDate(json["created"] as String),
            updated = JsonHelper.nullableStringTimestampToDate(json["updated"] as String?)
        )
    }

    override fun toJson(): Map<String, Any?> = mapOf(
        "osm_id" to osmId,
        "type" to locationType.offTag,
        "osm_type" to type?.offTag,
        "price_count" to priceCount,
        "user_count" to userCount,
        "product_count" to productCount,
        "proof_count" to proofCount,
        "id" to locationId,
        "osm_name" to name,
        "osm_display_name" to displayName,
        "osm_address_postcode" to postcode,
        "osm_address_city" to city,
        "osm_address_country" to country,
        "osm_address_country_code" to countryCode,
        "osm_tag_key" to osmKey,
        "osm_tag_value" to osmValue,
        "osm_lat" to latitude,
        "osm_lon" to longitude,
        "website_url" to websiteUrl,
        "created" to created.toIso8601String(),
        "updated" to updated?.toIso8601String()
    )
}