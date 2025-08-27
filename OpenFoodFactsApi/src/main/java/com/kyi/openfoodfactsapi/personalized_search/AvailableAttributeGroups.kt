package com.kyi.openfoodfactsapi.personalized_search

import com.kyi.openfoodfactsapi.models.AttributeGroup
import com.kyi.openfoodfactsapi.utils.HttpHelper

/**
 * Referential of attribute groups, with loader.
 */
class AvailableAttributeGroups(attributeGroupsString: String) {

    private var attributeGroups: List<AttributeGroup>? = null

    init {
        val inputJson = HttpHelper().jsonDecode(attributeGroupsString)
        val attributeGroupsList = mutableListOf<AttributeGroup>()
        for (item in inputJson as List<*>) {
            attributeGroupsList.add(AttributeGroup.fromJson(item as Map<String, Any?>))
        }
        if (attributeGroupsList.isEmpty()) {
            throw Exception("Unexpected error: empty attribute groups from json string $attributeGroupsString")
        }
        this.attributeGroups = attributeGroupsList
    }

    companion object {

        /**
         * Where a localized JSON file can be found.
         * [languageCode] is a 2-letter language code.
         */
        fun getUrl(languageCode: String): String = "https://world.openfoodfacts.org/api/v2/attribute_groups?lc=$languageCode"
    }
}