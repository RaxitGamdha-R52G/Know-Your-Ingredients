package com.kyi.openfoodfactsapi.prices


enum class GetPricesOrderField(override val offTag: String) : OrderByField {
    CREATED("created"),
    DATE("date"),
    PRICE("price")
}