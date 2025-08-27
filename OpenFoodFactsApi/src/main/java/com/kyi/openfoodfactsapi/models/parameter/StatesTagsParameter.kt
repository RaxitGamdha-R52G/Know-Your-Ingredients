package com.kyi.openfoodfactsapi.models.parameter

import ProductState

/**
 * States Tags as completed or to-be-completed.
 */
class StatesTagsParameter(map: Map<ProductState, Boolean>) : BoolMapParameter<ProductState>(map) {

    override fun getName(): String = "states_tags"

    override fun getTag(key: ProductState, value: Boolean): String =
        if (value) key.completedTag else key.toBeCompletedTag
}