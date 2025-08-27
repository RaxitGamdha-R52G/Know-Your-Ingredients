package com.kyi.openfoodfactsapi.prices


enum class GetLocationsOrderField(override val offTag: String) : OrderByField {
    PRICE_COUNT("price_count"),
    CREATED("created"),
    UPDATED("updated")
}