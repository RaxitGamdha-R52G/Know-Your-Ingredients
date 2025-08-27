package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class PalmOilFreeStatus(override val offTag: String) : OffTagged {
    PALM_OIL_FREE("en:palm-oil-free"),
    PALM_OIL("en:palm-oil"),
    MAY_CONTAIN_PALM_OIL("en:may-contain-palm-oil"),
    PALM_OIL_CONTENT_UNKNOWN("en:palm-oil-content-unknown");
}