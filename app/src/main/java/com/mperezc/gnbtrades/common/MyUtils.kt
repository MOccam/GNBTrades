package com.mperezc.gnbtrades.common

import com.mperezc.core.utils.CoreUtils
import com.mperezc.core.utils.CoreUtils.roundDouble

object MyUtils {

    /**
     * Return amount as formatted string
     * @param amount Amount to round
     * @param decimals Decimals to round
     *
     */
    fun roundDoubleAmount(
        amount: Double,
        decimals: Int = 2
    ): String {
        return CoreUtils.getFormattedNumber(roundDouble(amount, decimals), CoreUtils.PATTERN_TWO_DECIMAL)
    }

}