package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class ContributionKind(override val offTag: String) : OffTagged {
    CONSUMPTION("CONSUMPTION"),
    COMMUNITY("COMMUNITY");

    companion object {

        fun fromOffTag(offTag: String?): ContributionKind? = entries.firstOrNull { it.offTag == offTag }
    }
}