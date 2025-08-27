package com.kyi.openfoodfactsapi.models.parameter

import com.kyi.openfoodfactsapi.sources.Parameter

/**
 * "Page size" search API parameter
 *
 * Typically defaults to 50 (used to be 24). Max value seems to be 100.
 */
class PageSize(val size: Int) : Parameter() {

    override fun getName(): String = "page_size"

    override fun getValue(): String = size.toString()
}