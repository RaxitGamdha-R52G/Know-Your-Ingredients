package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.sources.JsonObject

abstract class CommonProofParameters : JsonObject() {

    abstract val type: ProofType?

    var date: DateTime? = null

    var currency: Currency? = null

    var locationOSMId: Int? = null

    var locationOSMType: LocationOSMType? = null

    var receiptPriceCount: Int? = null

    var receiptPriceTotal: Double? = null

    var receiptOnlineDeliveryCosts: Double? = null

    var readyForPriceTagValidation: Boolean? = null

    var ownerConsumption: Boolean? = null

    var ownerComment: String? = null

    var locationId: Int? = null

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "type" to type?.offTag,
        "date" to date?.let { GetParametersHelper.formatDate(it) },
        "currency" to currency?.name,
        "location_osm_id" to locationOSMId,
        "location_osm_type" to locationOSMType?.offTag,
        "receipt_price_count" to receiptPriceCount,
        "receipt_price_total" to receiptPriceTotal,
        "receipt_online_delivery_costs" to receiptOnlineDeliveryCosts,
        "ready_for_price_tag_validation" to readyForPriceTagValidation,
        "owner_consumption" to ownerConsumption,
        "owner_comment" to ownerComment,
        "location_id" to locationId
    )
}