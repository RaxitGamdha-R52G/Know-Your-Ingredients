package com.kyi.openfoodfactsapi.personalized_search

/**
 * Referential for product preferences: attribute groups and importance.
 */
class AvailableProductPreferences(
    preferenceImportancesString: String,
    attributeGroupsString: String
) {

    private var availableAttributeGroups: AvailableAttributeGroups? = null

    private var availablePreferenceImportances: AvailablePreferenceImportances? = null

    init {
        availableAttributeGroups = AvailableAttributeGroups(attributeGroupsString)
        availablePreferenceImportances = AvailablePreferenceImportances(preferenceImportancesString)
    }
}