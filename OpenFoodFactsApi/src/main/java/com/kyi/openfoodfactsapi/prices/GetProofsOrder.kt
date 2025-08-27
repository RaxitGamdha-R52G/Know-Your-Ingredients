package com.kyi.openfoodfactsapi.prices


enum class GetProofsOrderField(override val offTag: String) : OrderByField {
    PRICE_COUNT("price_count"),
    CREATED("created")
}