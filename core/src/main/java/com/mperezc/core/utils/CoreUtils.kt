package com.mperezc.core.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

object CoreUtils {

    //Decimal Patters
    const val PATTERN_NONE_DECIMAL = "#,###"
    const val PATTERN_ONE_DECIMAL = "#,###.0"
    const val PATTERN_TWO_DECIMAL = "#,###.00"

    /**
     * Get number with specific format. Apply current locale to output
     * @param number Number to formatter
     * @param pattern Desired output pattern
     * @return Number formatted in String
     */
    fun getFormattedNumber(number: Double, pattern: String): String {
        val decimalFormat = DecimalFormat(pattern)
        return decimalFormat.format(number)
    }

    /**
     * Round double
     * @param amount Amount to round
     * @param decimals Decimals to round
     * @param roundingMode Rounding mode
     */
    fun roundDouble(
        amount: Double,
        decimals: Int = 2,
        roundingMode: RoundingMode = RoundingMode.HALF_EVEN
    ): Double {
        return BigDecimal(amount).setScale(decimals, roundingMode).toDouble()
    }

}