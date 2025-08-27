package com.kyi.openfoodfactsapi.models.parameter

import com.kyi.openfoodfactsapi.sources.Parameter

/**
 * Possible sort options for search API
 */
enum class SortOption {
    POPULARITY,
    PRODUCT_NAME,
    CREATED,
    EDIT,
    NOTHING,
    ECOSCORE,
    NUTRISCORE,
}

/**
 * Sort search API parameter
 */
class SortBy(val option: SortOption?) : Parameter() {

    companion object {
        private val VALUES = mapOf(
            SortOption.PRODUCT_NAME to "product_name",
            SortOption.CREATED to "created_t",
            SortOption.EDIT to "last_modified_t",
            SortOption.POPULARITY to "unique_scans_n",
            SortOption.NOTHING to "nothing",
            SortOption.ECOSCORE to "ecoscore_score",
            SortOption.NUTRISCORE to "nutriscore_score",
        )
    }

    override fun getName(): String = "sort_by"

    override fun getValue(): String = VALUES[option] ?: "unique_scans_n"
}