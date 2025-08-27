package com.kyi.openfoodfactsapi.search

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class Fuzziness(override val offTag: String) : OffTagged {
    NONE("0"),
    ONE("1"),
    TWO("2")
}