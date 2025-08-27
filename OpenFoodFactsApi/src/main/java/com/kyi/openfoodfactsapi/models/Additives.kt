package com.kyi.openfoodfactsapi.models

class Additives(val ids: List<String>, val names: List<String>) {

    companion object {

        fun additivesFromJson(json: List<Any>?): Additives {
            val ids = mutableListOf<String>()
            val names = mutableListOf<String>()

            if (json == null) {
                return Additives(ids, names)
            }

            for (i in json.indices) {
                ids.add(json[i].toString())
                val name = "E${json[i].toString().substring(4)}" // remove the 'en:' header and Capitalize the 'E'.
                names.add(name)
            }

            return Additives(ids, names)
        }

        fun additivesToJson(additives: Additives?): List<String>? {
            val result = mutableListOf<String>()

            if (additives == null) {
                return null
            }

            for (i in additives.ids.indices) {
                result.add(additives.ids[i].toString())
            }

            return result
        }
    }
}