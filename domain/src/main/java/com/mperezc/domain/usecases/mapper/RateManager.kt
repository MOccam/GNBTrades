package com.mperezc.domain.usecases.mapper

import com.mperezc.domain.model.RateModel

class RateManager {

    fun creteCurrencyMap(ratesList: List<RateModel>): Map<Pair<String, String>, Double> {
        val currencyMap = mutableMapOf<Pair<String, String>, Double>()

        //Obtain currencies
        val currencies1 = ratesList.map { it.from }
        val currencies2 = ratesList.map { it.to }

        val fooSet: MutableSet<String> = LinkedHashSet(currencies1)
        fooSet.addAll(currencies2)
        val currencies: List<String> = ArrayList(fooSet)

        currencies.forEach { mFrom ->
            val otherCurrencies = currencies.filter { it != mFrom }
            otherCurrencies.forEach other@ { mTo ->
                val conversionRate = getConversionFactor(ratesList, mFrom, mTo)
                conversionRate?.let {
                    currencyMap[Pair(mFrom, mTo)] = it
                    return@other
                } ?: run {
                    return@other
                }
            }
        }
        return currencyMap
    }

    private fun getConversionFactor(
        ratesList: List<RateModel>,
        from: String,
        to: String,
        checked: HashSet<String> = hashSetOf(),
        rate: Double = 1.0
    ): Double? {
        val rateConversion = ratesList.firstOrNull { from == it.from && to == it.to }
        rateConversion?.let {
            return rate * (it.rate?: 1.0)
        } ?: run {
            ratesList.forEach {
                if (from == it.from && !checked.contains(it.to)) {
                    checked.add(from)
                    return getConversionFactor(
                        ratesList,
                        it.to!!,
                        to,
                        checked,
                        rate * (it.rate?: 1.0)
                    )
                }
            }
            return null
        }
    }
}