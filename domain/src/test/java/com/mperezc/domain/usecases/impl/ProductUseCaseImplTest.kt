package com.mperezc.domain.usecases.impl

import com.mperezc.domain.model.ProductModel
import com.mperezc.domain.usecases.CodeErrors
import com.mperezc.domain.usecases.Result
import com.mperezc.domain.usecases.impl.mock.MockRateUseCase
import com.mperezc.domain.usecases.impl.mock.MockTransactionUseCase
import com.mperezc.domain.usecases.succeeded
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class ProductUseCaseImplTest {

    private lateinit var productUseCase: ProductUseCaseImpl

    @Before
    fun setup() {
        productUseCase = ProductUseCaseImpl(
            MockRateUseCase(),
            MockTransactionUseCase()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `obtain default currency amount (from default)`() = runTest {
        val result = productUseCase.getProducts().first() //First emit
        val amount = obtainAmountProduct(MockTransactionUseCase.EUR_TRANSACTION, result)
        assert(amount == MockTransactionUseCase.EUR_TRANSACTION_AMOUNT)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `obtain default currency amount (form no default)`() = runTest {
        val result = productUseCase.getProducts().first() //First emit
        val amount = obtainAmountProduct(MockTransactionUseCase.CAD_TRANSACTION, result)
        assert(amount == MockTransactionUseCase.CAD_TRANSACTION_AMOUNT_EUR)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `on error rate data`() = runTest {
        val productFailureUseCase = ProductUseCaseImpl(
            MockRateUseCase().apply { setIsSuccessResponse(false) },
            MockTransactionUseCase()
        )
        val result = productFailureUseCase.getProducts().first() //First emit
        assert(!result.succeeded && (result as Result.Error).exception == CodeErrors.DATA_ERROR)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `on error transaction data`() = runTest {
        val productFailureUseCase = ProductUseCaseImpl(
            MockRateUseCase(),
            MockTransactionUseCase().apply { setIsSuccessResponse(false) }
        )
        val result = productFailureUseCase.getProducts().first() //First emit
        assert(!result.succeeded && (result as Result.Error).exception == CodeErrors.DATA_ERROR)
    }


    /**
     * Obtain amount for a specific product
     * @param sku Id product
     * @param result Response fake service with product list
     */
    private fun obtainAmountProduct(sku: String, result: Result<List<ProductModel>>): Double {
        return (result as Result.Success).data.first {
            sku == it.sku
        }.totalAmount
    }
}