package com.mperezc.domain.usecases.impl

import com.mperezc.domain.model.RateMapModel
import com.mperezc.domain.usecases.Result
import com.mperezc.domain.usecases.impl.mock.MockRateDataSource
import com.mperezc.domain.usecases.succeeded
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class RateUseCaseImplTest {

    @Before
    fun setUp() {
        //Nothing to do
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `obtain rate model from api model`() = runTest {
        val rateUseCaseImpl = RateUseCaseImpl(MockRateDataSource())

        val result = rateUseCaseImpl.getRate().first()
        assert(result is Result.Success<RateMapModel>)
        val rate = (result as Result.Success<RateMapModel>)
            .data.getChange(MockRateDataSource.FROM, MockRateDataSource.TO)
        assert(MockRateDataSource.RATE == rate)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `failure obtain rate model from api model`() = runTest {
        val rateUseCaseImpl = RateUseCaseImpl(MockRateDataSource().apply { setFailure() })

        val result = rateUseCaseImpl.getRate().first()
        assert(!result.succeeded)
    }

}