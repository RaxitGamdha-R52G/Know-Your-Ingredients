package com.kyi.openfoodfactsapi.personalized_search

import com.kyi.openfoodfactsapi.models.Attribute
import com.kyi.openfoodfactsapi.models.AttributeGroup

class ProductPreferencesManager(private val productPreferencesSelection: ProductPreferencesSelection) {

    private var availableProductPreferences: AvailableProductPreferences? = null

    fun getReferenceAttribute(attributeId: String): Attribute? {
        availableProductPreferences?.availableAttributeGroups?.attributeGroups?.forEach { attributeGroup ->
            attributeGroup.attributes?.forEach { attribute ->
                if (attribute.id == attributeId) return attribute
            }
        }
        return null
    }

    val attributeGroups: List<AttributeGroup>? get() = availableProductPreferences?.availableAttributeGroups?.attributeGroups

    private val availablePreferenceImportances: AvailablePreferenceImportances? get() = availableProductPreferences?.availablePreferenceImportances

    val importanceIds: List<String>? get() = availablePreferenceImportances?.importanceIds

    fun getImportanceIdForAttributeId(attributeId: String): String =
        productPreferencesSelection.getImportance(attributeId)

    suspend fun setImportance(
        attributeId: String,
        importanceId: String,
        notifyListeners: Boolean = true
    ) {
        await(productPreferencesSelection.setImportance(attributeId, importanceId))
        if (notifyListeners) notify()
    }

    fun getOrderedImportantAttributeIds(): List<String> {
        val map = mutableMapOf<Int, MutableList<String>>()
        attributeGroups?.forEach { attributeGroup ->
            attributeGroup.attributes?.forEach { attribute ->
                val attributeId = attribute.id!!
                val importanceId = getImportanceIdForAttributeId(attributeId)
                val importanceIndex =
                    availablePreferenceImportances?.getImportanceIndex(importanceId)
                if (importanceIndex != null) {
                    if (importanceIndex == PreferenceImportance.INDEX_NOT_IMPORTANT) return@forEach
                    var list = map[importanceIndex]
                    if (list == null) {
                        list = mutableListOf()
                        map[importanceIndex] = list
                    }
                    list.add(attributeId)
                }
            }
        }
        val result = mutableListOf<String>()
        if (map.isEmpty()) return result
        val decreasingImportances = map.keys.toMutableList()
        decreasingImportances.sortDescending()
        for (importance in decreasingImportances) {
            map[importance]?.let { result.addAll(it) }
        }
        return result
    }

    fun getAttributeIdsWithImportance(importanceId: String): List<String> {
        val result = mutableListOf<String>()
        attributeGroups?.forEach { attributeGroup ->
            attributeGroup.attributes?.forEach { attribute ->
                val attributeId = attribute.id!!
                val foundImportanceId = getImportanceIdForAttributeId(attributeId)
                if (importanceId == foundImportanceId) result.add(attributeId)
            }
        }
        return result
    }

    fun isAttributeImportant(attributeId: String): Boolean? {
        val importanceId = getImportanceIdForAttributeId(attributeId)
        val importanceIndex = availablePreferenceImportances?.getImportanceIndex(importanceId)
        if (importanceIndex == null) return null
        return importanceIndex != PreferenceImportance.INDEX_NOT_IMPORTANT
    }

    fun getPreferenceImportanceFromImportanceId(importanceId: String): PreferenceImportance? =
        availablePreferenceImportances?.getPreferenceImportance(importanceId)

    fun getImportanceIndex(importanceId: String): Int? =
        availablePreferenceImportances?.getImportanceIndex(importanceId)

    fun notify() = productPreferencesSelection.notify()

    suspend fun clearImportances(notifyListeners: Boolean = true) {
        attributeGroups?.forEach { attributeGroup ->
            attributeGroup.attributes?.forEach { attribute ->
                setImportance(
                    attribute.id!!,
                    PreferenceImportance.ID_NOT_IMPORTANT,
                    notifyListeners = false
                )
            }
        }
        if (notifyListeners) notify()
    }
}