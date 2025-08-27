package com.kyi.openfoodfactsapi.utils

enum class Unit {
    KCAL, KJ, G, MILLI_G, MICRO_G, MILLI_L, L, PERCENT, UNKNOWN
}

object UnitHelper {

    private val UNITS = mapOf(
        "kcal" to Unit.KCAL,
        "kCal" to Unit.KCAL,
        "KCal" to Unit.KCAL,
        "kj" to Unit.KJ,
        "Kj" to Unit.KJ,
        "kJ" to Unit.KJ,
        "KJ" to Unit.KJ,
        "g" to Unit.G,
        "G" to Unit.G,
        "mg" to Unit.MILLI_G,
        "milli-gram" to Unit.MILLI_G,
        "mG" to Unit.MILLI_G,
        "mcg" to Unit.MICRO_G,
        "µg" to Unit.MICRO_G,
        "&#181;g" to Unit.MICRO_G,
        "&micro;g" to Unit.MICRO_G,
        "&#xb5;g" to Unit.MICRO_G,
        "ml" to Unit.MILLI_L,
        "mL" to Unit.MILLI_L,
        "Ml" to Unit.MILLI_L,
        "ML" to Unit.MILLI_L,
        "milli-liter" to Unit.MILLI_L,
        "liter" to Unit.L,
        "L" to Unit.L,
        "l" to Unit.L,
        "%" to Unit.PERCENT,
        "per cent" to Unit.PERCENT,
        "percent" to Unit.PERCENT,
        "μg" to Unit.MICRO_G
    )

    private val POSSIBLE_SPELLINGS = mapOf(
        Unit.KCAL to "kcal",
        Unit.KJ to "kj",
        Unit.G to "g",
        Unit.MILLI_G to "mg",
        Unit.MICRO_G to "mcg",
        Unit.MILLI_L to "ml",
        Unit.L to "liter",
        Unit.PERCENT to "percent"
    )

    fun unitToString(unit: Unit?): String? = POSSIBLE_SPELLINGS[unit]

    fun stringToUnit(s: String?): Unit? {
        if (s == null || s.isEmpty()) return null
        if (s[0].code == 0x03BC) { // greek letter mu
            if (s.length > 1 && s.substring(1) == "g") return Unit.MICRO_G
            return Unit.UNKNOWN
        }
        return UNITS[s] ?: Unit.UNKNOWN
    }
}