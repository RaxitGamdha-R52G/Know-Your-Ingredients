package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.utils.JsonHelper

class PriceTotalStats(val json: Map<String, Any?>) {

    val priceCount: Int? get() = getInt("price_count")
    val priceTypeProductCodeCount: Int? get() = getInt("price_type_product_code_count")
    val priceTypeCategoryTagCount: Int? get() = getInt("price_type_category_tag_count")
    val priceWithDiscountCount: Int? get() = getInt("price_with_discount_count")
    val priceCurrencyCount: Int? get() = getInt("price_currency_count")
    val priceYearCount: Int? get() = getInt("price_year_count")
    val priceLocationCountryCount: Int? get() = getInt("price_location_country_count")
    val priceKindCommunityCount: Int? get() = getInt("price_kind_community_count")
    val priceKindConsumptionCount: Int? get() = getInt("price_kind_consumption_count")
    val priceSourceWebCount: Int? get() = getInt("price_source_web_count")
    val priceSourceMobileCount: Int? get() = getInt("price_source_mobile_count")
    val priceSourceApiCount: Int? get() = getInt("price_source_api_count")
    val priceSourceOtherCount: Int? get() = getInt("price_source_other_count")
    val productCount: Int? get() = getInt("product_count")
    val productSourceOffCount: Int? get() = getInt("product_source_off_count")
    val productSourceObfCount: Int? get() = getInt("product_source_obf_count")
    val productSourceOpffCount: Int? get() = getInt("product_source_opff_count")
    val productSourceOpfCount: Int? get() = getInt("product_source_opf_count")
    val productWithPriceCount: Int? get() = getInt("product_with_price_count")
    val productSourceOffWithPriceCount: Int? get() = getInt("product_source_off_with_price_count")
    val productSourceObfWithPriceCount: Int? get() = getInt("product_source_obf_with_price_count")
    val productSourceOpffWithPriceCount: Int? get() = getInt("product_source_opff_with_price_count")
    val productSourceOpfWithPriceCount: Int? get() = getInt("product_source_opf_with_price_count")
    val locationCount: Int? get() = getInt("location_count")
    val locationWithPriceCount: Int? get() = getInt("location_with_price_count")
    val locationTypeOsmCountryCount: Int? get() = getInt("location_type_osm_country_count")
    val proofCount: Int? get() = getInt("proof_count")
    val proofWithPriceCount: Int? get() = getInt("proof_with_price_count")
    val proofTypePriceTagCount: Int? get() = getInt("proof_type_price_tag_count")
    val proofTypeReceiptCount: Int? get() = getInt("proof_type_receipt_count")
    val proofTypeGdprRequestCount: Int? get() = getInt("proof_type_gdpr_request_count")
    val proofTypeShopImportCount: Int? get() = getInt("proof_type_shop_import_count")
    val proofKindCommunityCount: Int? get() = getInt("proof_kind_community_count")
    val proofKindConsumptionCount: Int? get() = getInt("proof_kind_consumption_count")
    val priceTagCount: Int? get() = getInt("price_tag_count")
    val priceTagStatusUnknownCount: Int? get() = getInt("price_tag_status_unknown_count")
    val priceTagStatusLinkedToPriceCount: Int? get() = getInt("price_tag_status_linked_to_price_count")
    val userCount: Int? get() = getInt("user_count")
    val userWithPriceCount: Int? get() = getInt("user_with_price_count")
    val updated: DateTime? get() = getDateTime("updated")

    private fun getInt(key: String): Int? = if (json.containsKey(key)) json[key] as Int? else null

    private fun getDateTime(key: String): DateTime? = JsonHelper.nullableStringTimestampToDate(json[key] as String?)

    fun toJson(): Map<String, Any?> = json
}