package com.kyi.openfoodfactsapi.models

enum class InsightAnnotation(val value: Int) {
    YES(1),
    NO(0),
    MAYBE(-1);

    companion object {
        fun fromInt(annotation: Int): InsightAnnotation? =
            entries.firstOrNull { it.value == annotation }
    }
}