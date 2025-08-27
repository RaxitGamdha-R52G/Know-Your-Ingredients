package com.kyi.openfoodfactsapi.sources

/**
 * Abstract search API parameter, to be used in the search URI.
 *
 * Typical use will be 'name=value'.
 */
abstract class Parameter {

    abstract fun getName(): String

    abstract fun getValue(): String

    override fun toString(): String = "&${getName()}=${getValue()}"
}