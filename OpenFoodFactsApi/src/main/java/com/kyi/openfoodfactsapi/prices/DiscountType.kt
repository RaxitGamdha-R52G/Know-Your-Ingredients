package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class DiscountType(override val offTag: String) : OffTagged {
    QUANTITY("QUANTITY"),
    SALE("SALE"),
    SEASONAL("SEASONAL"),
    LOYALTY_PROGRAM("LOYALTY_PROGRAM"),
    EXPIRES_SOON("EXPIRES_SOON"),
    PICK_IT_YOURSELF("PICK_IT_YOURSELF"),
    SECOND_HAND("SECOND_HAND"),
    OTHER("OTHER");

    companion object {

        fun fromOffTag(offTag: String?): DiscountType? = entries.firstOrNull { it.offTag == offTag }
    }
}