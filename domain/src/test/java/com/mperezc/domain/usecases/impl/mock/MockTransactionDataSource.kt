package com.mperezc.domain.usecases.impl.mock

import com.mperezc.core.middleware.ErrorCodes
import com.mperezc.core.middleware.RequestError
import com.mperezc.core.middleware.RequestResult
import com.mperezc.data.api.model.TransactionApiModel
import com.mperezc.data.source.TransactionDataSource

class MockTransactionDataSource: TransactionDataSource {

    private var isSuccess = true

    companion object {
        const val SKU = "T1000"
        const val AMOUNT = 12345.678
        const val CURRENCY = "EUR"
    }

    override suspend fun getTransactions(): RequestResult<List<TransactionApiModel>> {

        val apiModel = TransactionApiModel(
            sku = SKU,
            amount = AMOUNT,
            currency = CURRENCY
        )

        return if (isSuccess) {
            RequestResult.Success(
                listOf(apiModel)
            )
        } else {
            RequestResult.Error(
                RequestError(ErrorCodes.UNKNOWN.value, "")
            )
        }
    }

    fun setFailure() {
        isSuccess = false
    }
}