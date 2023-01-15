package com.mperezc.domain

import com.mperezc.domain.model.RateModel
import com.mperezc.domain.usecases.mapper.RateManager
import org.junit.Before
import org.junit.Test

class RateManagerTest {

    private lateinit var rateMapper: RateManager
    private lateinit var currencyMap: Map<Pair<String, String>, Double>

    private val mockRateList = listOf(
        RateModel("EUR", "USD",2.0),
        RateModel("CAD", "EUR",4.0),
        RateModel("USD", "EUR",0.5),
        RateModel("EUR", "CAD",0.25),
    )

    @Before
    fun setup() {
        rateMapper = RateManager()
        currencyMap = rateMapper.creteCurrencyMap(mockRateList)
    }

    @Test
    fun `direct currency conversion`() {
        /* 1 EUR to X USD
            1 EUR * 2 = 2 USD
        */

        val euro = 1.0
        val dollar: Double = euro * (currencyMap[Pair("EUR", "USD")] ?: 0.0)
        assert(dollar == 2.0)

    }

    @Test
    fun `indirect currency exchange`() {
        /* 1 CAD to X USD
            1 CAD * 4 = 4 EUR
            4 EUR * 2 = 8 USD
       */
        val cad = 1.0
        val dollar: Double = cad * (currencyMap[Pair("CAD", "USD")] ?: 0.0)
        assert(dollar == 8.0)
    }

    @Test
    fun `change EUR to XXX`() {
        // Change no exist -> Return NULL change
        val eur = 1.0
        val dollar: Double = eur * (currencyMap[Pair("EUR", "XXX")] ?: 0.0)
        assert(dollar == 0.0)
    }
}