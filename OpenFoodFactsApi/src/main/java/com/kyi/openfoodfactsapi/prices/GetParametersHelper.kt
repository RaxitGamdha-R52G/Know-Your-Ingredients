package com.kyi.openfoodfactsapi.prices


abstract class GetParametersHelper<T : OrderByField> {

    var pageSize: Int? = null
    var pageNumber: Int? = null
    var orderBy: List<OrderBy<T>>? = null
    var additionalParameters: Map<String, String>? = null

    protected val result = mutableMapOf<String, String>()

    open fun getQueryParameters(): Map<String, String> {
        checkIntValue("page_number", pageNumber, min = 1)
        checkIntValue("page_size", pageSize, min = 1, max = 100)
        result.clear()
        addNonNullInt(pageNumber, "page")
        addNonNullInt(pageSize, "size")
        orderBy?.let { nonNullOrderBy ->
            val orders = mutableListOf<String>()
            for (order in nonNullOrderBy) {
                orders.add(order.offTag)
            }
            if (orders.isNotEmpty()) {
                addNonNullString(orders.joinToString(","), "order_by")
            }
        }
        additionalParameters?.let { result.putAll(it) }
        return result
    }

    private fun checkIntValue(fieldDescription: String, value: Int?, min: Int? = null, max: Int? = null) {
        value ?: return
        min?.let { if (value < it) throw Exception("$fieldDescription minimum value is $it (actual value is $value)") }
        max?.let { if (value > it) throw Exception("$fieldDescription maximum value is $it (actual value is $value)") }
    }

    fun addNonNullString(value: String?, tag: String) {
        value?.let { result[tag] = it }
    }

    fun addNonNullInt(value: Int?, tag: String) = addNonNullString(value?.toString(), tag)

    fun addNonNullBool(value: Boolean?, tag: String) = addNonNullString(value?.toString(), tag)

    fun addNonNullDate(value: DateTime?, tag: String, dayOnly: Boolean) {
        value?.let { addNonNullString(formatDate(it, dayOnly = dayOnly), tag) }
    }

    companion object {

        fun formatDate(date: DateTime, dayOnly: Boolean = true): String = if (dayOnly) date.toIso8601String().substring(0, 10) else date.toIso8601String()
    }
}