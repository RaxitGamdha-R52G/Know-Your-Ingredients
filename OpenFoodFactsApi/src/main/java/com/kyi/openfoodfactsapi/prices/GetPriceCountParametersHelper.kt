package com.kyi.openfoodfactsapi.prices


abstract class GetPriceCountParametersHelper<T : OrderByField> : GetParametersHelper<T>() {

    var priceCount: Int? = null
    var priceCountGte: Int? = null
    var priceCountLte: Int? = null

    override fun getQueryParameters(): Map<String, String> {
        super.getQueryParameters()
        addNonNullInt(priceCount, "price_count")
        addNonNullInt(priceCountGte, "price_count__gte")
        addNonNullInt(priceCountLte, "price_count__lte")
        return result
    }
}