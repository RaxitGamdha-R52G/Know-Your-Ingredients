package com.kyi.openfoodfactsapi.personalized_search

class PreferenceImportance(
    val id: String? = null,
    val name: String? = null,
    val factor: Int? = null,
    val minimalMatch: Int? = null
) {

    companion object {

        fun fromJson(json: Any?): PreferenceImportance = PreferenceImportance(
            id = checkedId(json["id"] as String?),
            name = json["name"] as String?,
            factor = json["factor"] as Int?,
            minimalMatch = json["minimum_match"] as Int?
        )

        const val INDEX_NOT_IMPORTANT = 0

        const val ID_NOT_IMPORTANT = "not_important"
        const val ID_IMPORTANT = "important"
        const val ID_VERY_IMPORTANT = "very_important"
        const val ID_MANDATORY = "mandatory"

        val IDS = listOf(ID_NOT_IMPORTANT, ID_IMPORTANT, ID_VERY_IMPORTANT, ID_MANDATORY)

        private fun checkedId(id: String): String = if (IDS.contains(id)) id else throw Exception("Unknown id \"$id\"")
    }

    override fun toString(): String = "PreferenceImportance(id: $id, name: $name, factor: $factor, minimalWatch: $minimalMatch)"
}