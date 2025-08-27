package com.kyi.openfoodfactsapi.models.parameter


import AllergensTag

/**
 * List of allergens to filter in and out.
 *
 * When we have several items, the results returned use a logical AND.
 */
class AllergensParameter(map: Map<AllergensTag, Boolean>) : BoolMapParameter<AllergensTag>(map) {
    override fun getName(): String = "allergens_tags"

    override fun getTag(key: AllergensTag, value: Boolean): String =
        if (value) key.offTag else "-${key.offTag}"
}