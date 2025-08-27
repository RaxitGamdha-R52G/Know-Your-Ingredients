package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class VeganStatus(override val offTag: String) : OffTagged {
    VEGAN("en:vegan"),
    NON_VEGAN("en:non-vegan"),
    MAYBE_VEGAN("en:maybe-vegan"),
    VEGAN_STATUS_UNKNOWN("en:vegan-status-unknown");
}