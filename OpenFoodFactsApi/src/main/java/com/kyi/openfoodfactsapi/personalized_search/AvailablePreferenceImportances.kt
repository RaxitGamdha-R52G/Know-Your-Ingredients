package com.kyi.openfoodfactsapi.personalized_search

import com.kyi.openfoodfactsapi.utils.HttpHelper

/**
 * Referential of preference importance, with loader.
 */
class AvailablePreferenceImportances(preferenceImportancesString: String) {

    private var importanceIds: List<String>? = null

    private var preferenceImportances: Map<String, PreferenceImportance>? = null

    private var importancesReverseIds: Map<String, Int>? = null

    init {
        val importanceIdsList = mutableListOf<String>()
        val preferenceImportancesMap = mutableMapOf<String, PreferenceImportance>()
        val importancesReverseIdsMap = mutableMapOf<String, Int>()
        val inputJson = HttpHelper().jsonDecode(preferenceImportancesString)
        for (item in inputJson as List<*>) {
            val preferenceImportance = PreferenceImportance.fromJson(item as Map<String, Any?>)
            val id = preferenceImportance.id
            if (id != null) {
                preferenceImportancesMap[id] = preferenceImportance
                importanceIdsList.add(id)
                importancesReverseIdsMap[id] = importanceIdsList.size - 1
            }
        }
        if (importanceIdsList.isEmpty()) {
            throw Exception("Unexpected error: empty preference importance list from json string $preferenceImportancesString")
        }
        var i = 0
        for (preferenceImportanceId in importanceIdsList) {
            importancesReverseIdsMap[preferenceImportanceId] = i++
        }
        importanceIds = importanceIdsList
        preferenceImportances = preferenceImportancesMap
        importancesReverseIds = importancesReverseIdsMap
    }

    companion object {

        /**
         * Where a localized JSON file can be found.
         * [languageCode] is a 2-letter language code.
         */
        fun getUrl(languageCode: String): String =
            "https://world.openfoodfacts.org/api/v2/preferences?lc=$languageCode"
    }

    /**
     * Returns the index of an importance.
     *
     * From 0: not important.
     */
    fun getImportanceIndex(importanceId: String): Int =
        importancesReverseIds?.get(importanceId) ?: PreferenceImportance.INDEX_NOT_IMPORTANT

    /**
     * Returns the importance from its id.
     */
    fun getPreferenceImportance(importanceId: String): PreferenceImportance? =
        preferenceImportances?.get(importanceId)
            ?: preferenceImportances?.get(PreferenceImportance.ID_NOT_IMPORTANT)
}