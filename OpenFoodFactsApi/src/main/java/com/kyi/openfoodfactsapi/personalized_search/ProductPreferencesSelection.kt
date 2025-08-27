package com.kyi.openfoodfactsapi.personalized_search

class ProductPreferencesSelection(
    private val getImportance: (String) -> String,
    private val setImportance: suspend (String, String) -> Unit,
    private val notify: () -> Unit?
) {

    suspend fun setImportance(attributeId: String, importanceId: String) {
        setImportance(attributeId, importanceId)
    }

    fun getImportance(attributeId: String): String = getImportance(attributeId)

    fun notify() = notify.invoke()
}