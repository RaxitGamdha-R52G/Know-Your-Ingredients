package com.kyi.openfoodfactsapi.models.parameter

import com.kyi.openfoodfactsapi.sources.Parameter

/**
 * "Without Additives" search API parameter
 */
class WithoutAdditives : Parameter() {

    override fun getName(): String = "additives"

    override fun getValue(): String = "without"
}