package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class ProofType(override val offTag: String) : OffTagged {
    PRICE_TAG("PRICE_TAG"),
    RECEIPT("RECEIPT"),
    GDPR_REQUEST("GDPR_REQUEST"),
    SHOP_IMPORT("SHOP_IMPORT");

    companion object {

        fun fromOffTag(offTag: String?): ProofType? = entries.firstOrNull { it.offTag == offTag }
    }
}