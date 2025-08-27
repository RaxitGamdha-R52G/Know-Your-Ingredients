package com.kyi.openfoodfactsapi.utils

class BarcodeValidator private constructor() {

    companion object {

        fun isValid(barcode: String?): Boolean {
            if (barcode == null) return false
            return AbstractBarcodeValidator(barcode).isValid
        }
    }
}

private abstract class AbstractBarcodeValidator(val barcode: String, val modulus: Int) {

    val isValid: Boolean get() = calculateModulus(barcode) == 0

    private fun calculateModulus(code: String): Int {
        var total = 0
        for (i in code.indices) {
            val lth = code.length
            val leftPos = i + 1
            val rightPos = lth - i
            val charValue = charAsInt(code, i)
            total += weightedValue(charValue, leftPos, rightPos)
        }
        if (total == 0) throw Exception("Invalid code, sum is zero")
        return total % modulus
    }

    private fun charAsInt(code: String, position: Int): Int = code[position].code - CHAR_UNIT_ZERO

    abstract fun weightedValue(charValue: Int, leftPos: Int, rightPos: Int): Int

    companion object {

        const val CHAR_UNIT_ZERO = 48
        const val CHAR_UNIT_NINE = 57
    }
}

private class EANValidator(barcode: String) : AbstractBarcodeValidator(barcode, 10) {

    companion object {

        private val POSITION_WEIGHT = listOf(3, 1)

        const val EAN8_LENGTH = 8
        const val EAN13_LENGTH = 13

        fun isEAN(barcode: String): Boolean = (barcode.length == EAN8_LENGTH || barcode.length == EAN13_LENGTH) && isDigitsOnly(barcode)

        private fun isDigitsOnly(str: String): Boolean {
            for (char in str) {
                if (char.code < CHAR_UNIT_ZERO || char.code > CHAR_UNIT_NINE) return false
            }
            return true
        }
    }

    override fun weightedValue(charValue: Int, leftPos: Int, rightPos: Int): Int {
        val weight = POSITION_WEIGHT[rightPos % 2]
        return charValue * weight
    }
}