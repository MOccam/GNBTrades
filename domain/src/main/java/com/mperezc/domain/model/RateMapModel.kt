package com.mperezc.domain.model

import com.mperezc.core.utils.CoreUtils

data class RateMapModel(
    private val ratesMap: Map<Pair<String, String>, Double>,
    private val timestamp: Long
) {
    fun applyCurrencyExchange(from: String, to: String, amount: Double?): Double? {
        return amount?.let {
            ratesMap[Pair(from, to)]?.let { changeFactor -> CoreUtils.roundDouble(changeFactor * it) }
        }
    }

    fun getChange(from: String, to: String): Double? {
        return ratesMap[Pair(from, to)]
    }

}
