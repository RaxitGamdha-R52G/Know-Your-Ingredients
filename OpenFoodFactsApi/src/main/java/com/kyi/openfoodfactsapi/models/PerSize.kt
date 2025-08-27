package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class PerSize(override val offTag: String) : OffTagged {
    SERVING("serving"),
    ONE_HUNDRED_GRAMS("100g");

    companion object {

        fun fromOffTag(offTag: String?): PerSize? = entries.firstOrNull { it.offTag == offTag }
    }
}