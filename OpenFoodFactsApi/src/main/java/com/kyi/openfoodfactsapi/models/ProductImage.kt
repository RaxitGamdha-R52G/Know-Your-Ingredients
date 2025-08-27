package com.kyi.openfoodfactsapi.models

import OpenFoodFactsLanguage
import ImageField
import ImageSize
import ImageAngle
import UriProductHelper

class ProductImage(
    val field: ImageField? = null,
    val size: ImageSize? = null,
    val language: OpenFoodFactsLanguage? = null,
    var url: String? = null,
    var uploaded: DateTime? = null,
    var contributor: String? = null,
    var rev: Int? = null,
    var imgid: String? = null,
    var angle: ImageAngle? = null,
    var coordinatesImageSize: String? = null,
    var x1: Int? = null,
    var y1: Int? = null,
    var x2: Int? = null,
    var y2: Int? = null,
    var width: Int? = null,
    var height: Int? = null
) {

    val isMain: Boolean get() = field != null && language != null

    fun getUrl(barcode: String, imageSize: ImageSize? = null, uriHelper: UriProductHelper = UriHelper.foodProd): String =
        "${uriHelper.getProductImageRootUrl(barcode)}/" + getUrlFilename(imageSize = imageSize)

    fun getUrlFilename(imageSize: ImageSize? = null): String {
        val bestImageSize = imageSize ?: size ?: ImageSize.UNKNOWN
        return if (isMain) getMainUrlFilename(bestImageSize) else getRawUrlFilename(bestImageSize)
    }

    private fun getMainUrlFilename(imageSize: ImageSize): String =
        "${field!!.offTag}_${language.code}.$rev.${imageSize.number}.jpg"

    private fun getRawUrlFilename(imageSize: ImageSize): String {
        return when (imageSize) {
            ImageSize.THUMB, ImageSize.DISPLAY -> "$imgid.${imageSize.number}.jpg"
            ImageSize.SMALL -> "$imgid.${ImageSize.DISPLAY.number}.jpg"
            ImageSize.ORIGINAL, ImageSize.UNKNOWN -> "$imgid.jpg"
        }
    }

    override fun toString(): String = "ProductImage(" +
            "${if (field == null) "" else "field=${field!!.offTag}"}" +
            "${if (size == null) "" else ",size=${size!!.offTag}"}" +
            "${if (language == null) "" else ",language=${language.code}"}" +
            "${if (angle == null) "" else ",angle=${angle!!.degreesClockwise}"}" +
            "${if (url == null) "" else ",url=$url"}" +
            "${if (uploaded == null) "" else ",uploaded=$uploaded"}" +
            "${if (contributor == null) "" else ",uploader=$contributor"}" +
            "${if (imgid == null) "" else ",imgid=$imgid"}" +
            "${if (rev == null) "" else ",rev=$rev"}" +
            "${if (coordinatesImageSize == null) "" else ",coordinatesImageSize=$coordinatesImageSize"}" +
            "${if (x1 == null) "" else ",x1=$x1"}" +
            "${if (y1 == null) "" else ",y1=$y1"}" +
            "${if (x2 == null) "" else ",x2=$x2"}" +
            "${if (y2 == null) "" else ",y2=$y2"}" +
            "${if (width == null) "" else ",width=$width"}" +
            "${if (height == null) "" else ",height=$height"}" +
            ")"

    private val key: String get() = "${field?.offTag}_${language?.code}_${size?.offTag}_$imgid"

    override fun hashCode(): Int = key.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductImage

        if (field != other.field) return false
        if (size != other.size) return false
        if (language != other.language) return false
        if (url != other.url) return false
        if (uploaded != other.uploaded) return false
        if (contributor != other.contributor) return false
        if (rev != other.rev) return false
        if (imgid != other.imgid) return false
        if (angle != other.angle) return false
        if (coordinatesImageSize != other.coordinatesImageSize) return false
        if (x1 != other.x1) return false
        if (y1 != other.y1) return false
        if (x2 != other.x2) return false
        if (y2 != other.y2) return false
        if (width != other.width) return false
        if (height != other.height) return false

        return true
    }
}