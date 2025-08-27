package com.kyi.openfoodfactsapi.utils

import com.kyi.openfoodfactsapi.models.AttributeGroup
import com.kyi.openfoodfactsapi.models.Ingredient
import com.kyi.openfoodfactsapi.models.ProductImage
import com.kyi.openfoodfactsapi.models.ProductPackaging
import com.kyi.openfoodfactsapi.sources.JsonObject

import ImageField
import ImageSize
import ImageAngle

object JsonHelper {

    fun selectedImagesFromJson(json: Any?): List<ProductImage>? {
        if (json == null || json !is Map<*, *>) return null

        val imageList = mutableListOf<ProductImage>()
        for (field in ImageField.entries) {
            for (size in ImageSize.entries) {
                for (lang in OpenFoodFactsLanguage.entries) {
                    (json[field.offTag] as Map<String, Any?>?)?.let { sizeJson ->
                        (sizeJson[size.offTag] as Map<String, Any?>?)?.let { langJson ->
                            val url = langJson[lang.offTag] as String?
                            url?.let {
                                val image = ProductImage(field = field, size = size, language = lang, url = it)
                                imageList.add(image)
                            }
                        }
                    }
                }
            }
        }
        return imageList
    }

    fun selectedImagesToJson(images: List<ProductImage>?): Map<String, Any> {
        val result = mutableMapOf<String, Any>()
        if (images == null) return result

        for (field in ImageField.entries) {
            val fieldMap = mutableMapOf<String, Any>()
            for (size in ImageSize.entries) {
                val sizeMap = mutableMapOf<String, String?>()
                for (image in images) {
                    if (image.field == field && image.size == size) {
                        sizeMap[image.language.code] = image.url
                    }
                }
                fieldMap[size.offTag] = sizeMap
            }
            result[field.offTag] = fieldMap
        }

        return result
    }

    private const val ALL_IMAGES_TAG_REVISION = "rev"
    private const val ALL_IMAGES_TAG_ANGLE = "angle"
    private const val ALL_IMAGES_TAG_COORDINATES = "coordinates_image_size"
    private const val ALL_IMAGES_TAG_X1 = "x1"
    private const val ALL_IMAGES_TAG_Y1 = "y1"
    private const val ALL_IMAGES_TAG_X2 = "x2"
    private const val ALL_IMAGES_TAG_Y2 = "y2"

    private const val ALL_IMAGES_TAG_UPLOADED = "uploaded_t"
    private const val ALL_IMAGES_TAG_UPLOADER = "uploader"

    private const val ALL_IMAGES_TAG_IMAGE_ID = "imgid"
    private const val ALL_IMAGES_TAG_WIDTH = "w"
    private const val ALL_IMAGES_TAG_HEIGHT = "h"
    private const val ALL_IMAGES_TAG_SIZES = "sizes"
    private const val ALL_IMAGES_TAG_URL = "url"

    fun allImagesFromJson(json: Any?, onlyMain: Boolean = false): List<ProductImage>? {
        if (json == null || json !is Map<*, *>) return null

        val imageList = mutableListOf<ProductImage>()

        for (key in json.keys) {
            var field: ImageField? = null
            var lang: OpenFoodFactsLanguage? = null
            val imageId = key.toString().toIntOrNull()
            if (imageId != null) {
                if (onlyMain) continue
            } else {
                val values = key.toString().split("_")
                if (values.size != 2) continue
                val fieldString = values[0]
                field = ImageField.fromOffTag(fieldString) ?: continue
                val languageString = values[1]
                lang = OpenFoodFactsLanguage.fromOffTag(languageString) ?: continue
            }

            val fieldObject = json[key] as Map<String, Any?>

            val sizesObject = fieldObject[ALL_IMAGES_TAG_SIZES] as Map<String, Any?>? ?: continue

            if (imageId != null) {
                val uploaded = timestampToDate(fieldObject[ALL_IMAGES_TAG_UPLOADED])
                val contributor = fieldObject[ALL_IMAGES_TAG_UPLOADER] as String?
                for (size in ImageSize.entries) {
                    val number = size.number
                    val numberObject = sizesObject[number] as Map<String, Any?>? ?: continue
                    imageList.add(
                        ProductImage.raw(
                            size = size,
                            imgid = imageId.toString(),
                            width = JsonObject.parseInt(numberObject[ALL_IMAGES_TAG_WIDTH]),
                            height = JsonObject.parseInt(numberObject[ALL_IMAGES_TAG_HEIGHT]),
                            url = numberObject[ALL_IMAGES_TAG_URL] as String?,
                            uploaded = uploaded,
                            contributor = contributor
                        )
                    )
                }
                continue
            }

            val rev = JsonObject.parseInt(fieldObject[ALL_IMAGES_TAG_REVISION])
            val imgid = fieldObject[ALL_IMAGES_TAG_IMAGE_ID].toString()
            val angle = ImageAngle.fromInt(JsonObject.parseInt(fieldObject[ALL_IMAGES_TAG_ANGLE]))
            val coordinatesImageSize = fieldObject[ALL_IMAGES_TAG_COORDINATES]?.toString()
            val x1 = JsonObject.parseInt(fieldObject[ALL_IMAGES_TAG_X1])
            val y1 = JsonObject.parseInt(fieldObject[ALL_IMAGES_TAG_Y1])
            val x2 = JsonObject.parseInt(fieldObject[ALL_IMAGES_TAG_X2])
            val y2 = JsonObject.parseInt(fieldObject[ALL_IMAGES_TAG_Y2])

            for (size in ImageSize.entries) {
                val number = size.number
                val numberObject = sizesObject[number] as Map<String, Any?>? ?: continue
                val width = JsonObject.parseInt(numberObject[ALL_IMAGES_TAG_WIDTH])
                val height = JsonObject.parseInt(numberObject[ALL_IMAGES_TAG_HEIGHT])
                val url = numberObject[ALL_IMAGES_TAG_URL] as String?

                val image = ProductImage(
                    field = field!!,
                    size = size,
                    language = lang!!,
                    rev = rev,
                    imgid = imgid,
                    angle = angle,
                    coordinatesImageSize = coordinatesImageSize,
                    x1 = x1,
                    y1 = y1,
                    x2 = x2,
                    y2 = y2,
                    width = width,
                    height = height,
                    url = url
                )
                imageList.add(image)
            }
        }

        return imageList
    }

    fun allImagesToJson(images: List<ProductImage>?, onlyMain: Boolean = false): Map<String, Any> {
        val result = mutableMapOf<String, Any>()
        if (images == null || images.isEmpty()) return result
        val sorted = mutableMapOf<String, MutableList<ProductImage>>()
        for (productImage in images) {
            val key = if (productImage.language != null && productImage.field != null) {
                "${productImage.field!!.offTag}_${productImage.language!!.offTag}"
            } else {
                if (onlyMain) continue
                productImage.imgid!!.toString()
            }
            var items = sorted[key]
            if (items == null) {
                items = mutableListOf()
                sorted[key] = items
            }
            items.add(productImage)
        }
        for ((key, list) in sorted) {
            if (list.isEmpty()) continue
            val item = mutableMapOf<String, Any>()
            item[ALL_IMAGES_TAG_SIZES] = mutableMapOf<String, Map<String, Any>>()
            var first = true
            for (productImage in list) {
                productImage.size?.let { size ->
                    val sizeMap = mutableMapOf<String, Any>()
                    productImage.width?.let { sizeMap[ALL_IMAGES_TAG_WIDTH] = it }
                    productImage.height?.let { sizeMap[ALL_IMAGES_TAG_HEIGHT] = it }
                    productImage.url?.let { sizeMap[ALL_IMAGES_TAG_URL] = it }
                    (item[ALL_IMAGES_TAG_SIZES] as MutableMap<String, Map<String, Any>>)[size.number] = sizeMap
                    if (first) {
                        first = false
                        if (!productImage.isMain) {
                            productImage.uploaded?.let { item[ALL_IMAGES_TAG_UPLOADED] = dateToTimestamp(it) }
                            productImage.contributor?.let { item[ALL_IMAGES_TAG_UPLOADER] = it }
                        } else {
                            productImage.rev?.let { item[ALL_IMAGES_TAG_REVISION] = it.toString() }
                            productImage.imgid?.let { item[ALL_IMAGES_TAG_IMAGE_ID] = it }
                            productImage.angle?.let { item[ALL_IMAGES_TAG_ANGLE] = it.degree.toString() }
                            productImage.coordinatesImageSize?.let { item[ALL_IMAGES_TAG_COORDINATES] = it }
                            productImage.x1?.let { item[ALL_IMAGES_TAG_X1] = it }
                            productImage.y1?.let { item[ALL_IMAGES_TAG_Y1] = it }
                            productImage.x2?.let { item[ALL_IMAGES_TAG_X2] = it }
                            productImage.y2?.let { item[ALL_IMAGES_TAG_Y2] = it }
                        }
                    }
                }
            }
            result[key] = item
        }
        return result
    }

    fun quantityFromJson(data: Any?): Double? {
        if (data == null || data is Double) return data
        if (data is Int) return data.toDouble()
        return data.toString().toDoubleOrNull()
    }

    fun ingredientsToJson(ingredients: List<Ingredient>?): List<Map<String, Any?>>? {
        if (ingredients == null || ingredients.isEmpty()) return null
        val result = mutableListOf<Map<String, Any?>>()
        for (ingredient in ingredients) {
            result.add(ingredient.toJson())
        }
        return result
    }

    fun productPackagingsToJson(packagings: List<ProductPackaging>?): List<Map<String, Any?>>? {
        if (packagings == null) return null
        val result = mutableListOf<Map<String, Any?>>()
        for (p in packagings) {
            result.add(p.toJson())
        }
        return result
    }

    fun attributeGroupsToJson(list: List<AttributeGroup>?): List<Map<String, Any?>>? {
        if (list == null || list.isEmpty()) return null
        val result = mutableListOf<Map<String, Any?>>()
        for (item in list) {
            result.add(item.toJson())
        }
        return result
    }

    fun timestampToDate(json: Any?): DateTime? {
        if (json == null) return null
        val timestamp = JsonObject.parseInt(json)!!
        return DateTime.fromMillisecondsSinceEpoch(Duration.millisecondsPerSecond * timestamp, isUtc = true)
    }

    fun dateToTimestamp(dateTime: DateTime?): Int? {
        if (dateTime == null) return null
        return (dateTime.toUtc().millisecondsSinceEpoch / Duration.millisecondsPerSecond).round()
    }

    fun stringTimestampToDate(json: String): DateTime = DateTime.parse(json)

    fun nullableStringTimestampToDate(json: Any?): DateTime? = json?.let { stringTimestampToDate(it as String) }

    private const val CHECKBOX_ON_VALUE = "on"
    private const val CHECKBOX_OFF_VALUE = ""

    fun checkboxFromJSON(jsonValue: Any?): Boolean? {
        if (jsonValue == null) return null
        return jsonValue is String && jsonValue.trim().lowercase() == CHECKBOX_ON_VALUE
    }

    fun checkboxToJSON(value: Any?): String? {
        if (value == null) return null
        return if (value == true || (value is String && value.trim().lowercase() == CHECKBOX_ON_VALUE)) CHECKBOX_ON_VALUE else CHECKBOX_OFF_VALUE
    }

    fun boolFromJSON(jsonValue: Any?): Boolean? {
        if (jsonValue == null) return null
        return jsonValue == 1
    }

    fun boolToJSON(value: Any?): Int? {
        if (value == null) return null
        return if (value == true || value == 1) 1 else 0
    }

    fun stringFromJSON(jsonValue: Any?): String? = jsonValue?.toString()
}