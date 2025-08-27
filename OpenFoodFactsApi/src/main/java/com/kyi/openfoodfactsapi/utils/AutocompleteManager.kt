package com.kyi.openfoodfactsapi.utils


class AutocompleteManager(private val autocompleter: Autocompleter) : Autocompleter {

    private val inputs = mutableListOf<String>()

    private val cache = mutableMapOf<String, List<String>>()

    override suspend fun getSuggestions(input: String): List<String> {
        inputs.add(input)
        cache[input]?.let { return it }
        waitForTestPurpose()
        cache[input] = autocompleter.getSuggestions(input)
        for (latestInput in inputs.reversed()) {
            cache[latestInput]?.let { return it }
        }
        return emptyList()
    }

    protected suspend fun waitForTestPurpose() {}
}