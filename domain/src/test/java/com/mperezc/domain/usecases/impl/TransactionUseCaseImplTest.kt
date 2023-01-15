package com.mperezc.domain.usecases.impl

import com.mperezc.domain.model.RateMapModel
import com.mperezc.domain.model.TransactionModel
import com.mperezc.domain.usecases.Result
import com.mperezc.domain.usecases.impl.mock.MockRateDataSource
import com.mperezc.domain.usecases.impl.mock.MockTransactionDataSource
import com.mperezc.domain.usecases.succeeded
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class TransactionUseCaseImplTest {

    @Before
    fun setUp() {
        //Nothing to do
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `obtain transaction model from api model`() = runTest {
        val rateUseCaseImpl = TransactionUseCaseImpl(MockTransactionDataSource())

        val result = rateUseCaseImpl.getTransactions().first()
        assert(result is Result.Success<List<TransactionModel>>)
        val transaction = (result as Result.Success<List<TransactionModel>>)
            .data.first()

        assert(
            MockTransactionDataSource.SKU == transaction.sku
                    && MockTransactionDataSource.AMOUNT == transaction.amount
                    && MockTransactionDataSource.CURRENCY == transaction.currency
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `failure obtain transaction model from api model`() = runTest {
        val rateUseCaseImpl = TransactionUseCaseImpl(MockTransactionDataSource().apply { setFailure() })

        val result = rateUseCaseImpl.getTransactions().first()
        assert(!result.succeeded)
    }
}