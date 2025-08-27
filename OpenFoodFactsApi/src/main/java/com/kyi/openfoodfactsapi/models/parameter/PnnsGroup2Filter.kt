package com.kyi.openfoodfactsapi.models.parameter

import com.kyi.openfoodfactsapi.sources.Parameter
import PnnsGroup2

/**
 * [PnnsGroup2] search API parameter
 */
class PnnsGroup2Filter(val pnnsGroup2: PnnsGroup2) : Parameter() {

    override fun getName(): String = "pnns_groups_2_tags"

    override fun getValue(): String = pnnsGroup2.offTag
}