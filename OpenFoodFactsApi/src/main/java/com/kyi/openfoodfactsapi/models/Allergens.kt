package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.OffTagged


/**
 * List of known allergens for a [Product].
 *
 * If we are lucky, we get values that match with [AllergensTag].
 * If we are less lucky, we have more exotic values.
 */
class Allergens(val ids: List<String>, val names: List<String>) {

    companion object {

        fun allergensFromJson(json: List<Any>?): Allergens {
            val ids = mutableListOf<String>()
            val names = mutableListOf<String>()

            if (json == null) {
                return Allergens(ids, names)
            }

            for (i in json.indices) {
                ids.add(json[i].toString())
                val name = json[i].toString().substring(3) // remove the 'en:' header.
                names.add(name)
            }

            return Allergens(ids, names)
        }

        fun allergensToJson(allergens: Allergens?): List<String>? {
            val result = mutableListOf<String>()

            if (allergens == null) {
                return null
            }

            for (i in allergens.ids.indices) {
                result.add(allergens.ids[i].toString())
            }

            return result
        }
    }
}