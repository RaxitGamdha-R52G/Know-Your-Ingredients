package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.OpenPricesAPIClient
import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.utils.JsonHelper
import com.kyi.openfoodfactsapi.utils.UriProductHelper

class Proof(
    var id: Int,
    var filePath: String? = null,
    var imageThumbPath: String? = null,
    var mimetype: String,
    var type: ProofType? = null,
    var priceCount: Int,
    var receiptPriceCount: Int? = null,
    var receiptPriceTotal: Double? = null,
    var locationOSMId: Int? = null,
    var locationOSMType: LocationOSMType? = null,
    var locationId: Int? = null,
    var date: DateTime? = null,
    var currency: Currency? = null,
    var owner: String,
    var created: DateTime,
    var updated: DateTime? = null,
    var location: Location? = null,
    var receiptOnlineDeliveryCosts: Double? = null,
    var readyForPriceTagValidation: Boolean? = null,
    var ownerConsumption: Boolean? = null,
    var ownerComment: String? = null,
    var source: String? = null
) : JsonObject() {

    companion object {

        fun fromJson(json: Map<String, Any?>): Proof = Proof(
            id = json["id"] as Int,
            filePath = json["file_path"] as String?,
            imageThumbPath = json["image_thumb_path"] as String?,
            mimetype = json["mimetype"] as String,
            type = ProofType.fromOffTag(json["type"] as String?),
            priceCount = json["price_count"] as Int,
            receiptPriceCount = json["receipt_price_count"] as Int?,
            receiptPriceTotal = json["receipt_price_total"] as Double?,
            locationOSMId = json["location_osm_id"] as Int?,
            locationOSMType = LocationOSMType.fromOffTag(json["location_osm_type"] as String?),
            locationId = json["location_id"] as Int?,
            date = JsonHelper.nullableStringTimestampToDate(json["date"] as String?),
            currency = Currency.fromName(json["currency"] as String?),
            owner = json["owner"] as String,
            created = JsonHelper.stringTimestampToDate(json["created"] as String),
            updated = JsonHelper.nullableStringTimestampToDate(json["updated"] as String?),
            location = json["location"]?.let { Location.fromJson(it as Map<String, Any?>) },
            receiptOnlineDeliveryCosts = json["receipt_online_delivery_costs"] as Double?,
            readyForPriceTagValidation = json["ready_for_price_tag_validation"] as Boolean?,
            ownerConsumption = json["owner_consumption"] as Boolean?,
            ownerComment = json["owner_comment"] as String?,
            source = json["source"] as String?
        )
    }

    override fun toJson(): Map<String, Any?> = mapOf(
        "id" to id,
        "file_path" to filePath,
        "image_thumb_path" to imageThumbPath,
        "mimetype" to mimetype,
        "type" to type?.offTag,
        "price_count" to priceCount,
        "receipt_price_count" to receiptPriceCount,
        "receipt_price_total" to receiptPriceTotal,
        "location_osm_id" to locationOSMId,
        "location_osm_type" to locationOSMType?.offTag,
        "location_id" to locationId,
        "date" to date?.toIso8601String(),
        "currency" to currency?.name,
        "owner" to owner,
        "created" to created.toIso8601String(),
        "updated" to updated?.toIso8601String(),
        "location" to location,
        "receipt_online_delivery_costs" to receiptOnlineDeliveryCosts,
        "ready_for_price_tag_validation" to readyForPriceTagValidation,
        "owner_consumption" to ownerConsumption,
        "owner_comment" to ownerComment,
        "source" to source
    )

    fun getFileUrl(uriProductHelper: UriProductHelper, isThumbnail: Boolean = false): Uri? =
        getFileUrlStatic(
            uriProductHelper = uriProductHelper,
            path = if (isThumbnail) imageThumbPath else filePath
        )

    private fun getFileUrlStatic(uriProductHelper: UriProductHelper, path: String?): Uri? =
        path?.let {
            OpenPricesAPIClient.getUri(
                path = "img/$it",
                uriHelper = uriProductHelper,
                addUserAgentParameters = false
            )
        }
}