package com.kyi.openfoodfactsapi.utils

interface Autocompleter {

    suspend fun getSuggestions(input: String): List<String>
}