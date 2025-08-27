package com.kyi.openfoodfactsapi.models.parameter

import com.kyi.openfoodfactsapi.sources.Parameter

/**
 * Abstract map of [Boolean] as [Parameter].
 *
 * Typical use-case with objects that have an on/off quality,
 * like "with/without gluten".
 * A query like "I want the products with eggs but without gluten" would be
 * something like "{'eggs': true, 'gluten': false}".
 */
abstract class BoolMapParameter<T>(val map: Map<T, Boolean>) : Parameter() {

    override fun getValue(): String {
        val result = mutableListOf<String>()
        for ((key, value) in map) {
            result.add(getTag(key, value))
        }
        return result.joinToString(",")
    }

    /**
     * Returns the tag as on or off, like "gluten:with" or "gluten:without"
     */
    protected abstract fun getTag(key: T, value: Boolean): String
}