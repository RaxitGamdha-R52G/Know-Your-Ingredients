package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.sources.JsonObject

class UpdatePriceParameters : JsonObject() {

    var price: Double? = null

    var priceIsDiscounted: Boolean? = null

    var priceWithoutDiscount: Double? = null

    var discountType: DiscountType? = null

    var pricePer: PricePer? = null

    var currency: Currency? = null

    var date: DateTime? = null

    var receiptQuantity: Int? = null

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "price_per" to pricePer?.offTag,
        "price_is_discounted" to priceIsDiscounted,
        "discount_type" to if (priceIsDiscounted == false) null else discountType?.offTag,
        "price_without_discount" to if (priceIsDiscounted == false) null else priceWithoutDiscount,
        "price" to price,
        "currency" to currency?.name,
        "date" to date?.let { GetParametersHelper.formatDate(it) },
        "receipt_quantity" to receiptQuantity
    )
}