package com.kyi.openfoodfactsapi.prices


enum class GetUsersOrderField(override val offTag: String) : OrderByField {
    PRICE_COUNT("price_count"),
    USER_ID("user_id")
}