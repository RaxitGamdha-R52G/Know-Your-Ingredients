package com.kyi.openfoodfactsapi.utils

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class OcrField(override val offTag: String) : OffTagged {
    TESSERACT("tesseract"),
    GOOGLE_CLOUD_VISION("google_cloud_vision")
}