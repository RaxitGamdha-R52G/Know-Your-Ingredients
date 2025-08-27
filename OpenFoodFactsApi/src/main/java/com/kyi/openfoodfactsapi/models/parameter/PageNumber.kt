package com.kyi.openfoodfactsapi.models.parameter

import com.kyi.openfoodfactsapi.sources.Parameter

/**
 * "Page number" search API parameter
 */
class PageNumber(val page: Int) : Parameter() {

    override fun getName(): String = "page"

    override fun getValue(): String = page.toString()
}