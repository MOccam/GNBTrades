package com.mperezc.domain.usecases.impl.mock

import com.mperezc.core.utils.CoreUtils
import com.mperezc.domain.model.TransactionModel
import com.mperezc.domain.usecases.CodeErrors
import com.mperezc.domain.usecases.Result
import com.mperezc.domain.usecases.TransactionUseCase
import com.mperezc.domain.usecases.impl.mock.MockRateUseCase.Companion.EXCHANGE_CAD_EUR
import com.mperezc.domain.usecases.impl.mock.MockRateUseCase.Companion.EXCHANGE_USD_EUR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockTransactionUseCase : TransactionUseCase {

    companion object {
        const val EUR_TRANSACTION = "T_EUR"
        private const val EUR_TRANSACTION_AMOUNT_1 = 2000.13
        private const val EUR_TRANSACTION_AMOUNT_2 = 1987.47
        const val EUR_TRANSACTION_AMOUNT = EUR_TRANSACTION_AMOUNT_1 + EUR_TRANSACTION_AMOUNT_2

        const val CAD_TRANSACTION = "T_CAD"
        private const val CAD_TRANSACTION_AMOUNT_1 = 2000.13
        private val CAD_TRANSACTION_AMOUNT_1_EUR = CoreUtils.roundDouble(CAD_TRANSACTION_AMOUNT_1 *EXCHANGE_CAD_EUR)
        private const val CAD_TRANSACTION_AMOUNT_2 = 1987.47
        private val CAD_TRANSACTION_AMOUNT_2_EUR = CoreUtils.roundDouble(CAD_TRANSACTION_AMOUNT_2 *EXCHANGE_CAD_EUR)
        val CAD_TRANSACTION_AMOUNT_EUR = CAD_TRANSACTION_AMOUNT_1_EUR + CAD_TRANSACTION_AMOUNT_2_EUR

        const val USD_TRANSACTION = "T_USD"
        private const val USD_TRANSACTION_AMOUNT_1 = 2000.13
        private val USD_TRANSACTION_AMOUNT_1_EUR = CoreUtils.roundDouble(USD_TRANSACTION_AMOUNT_1 *EXCHANGE_USD_EUR)
        private const val USD_TRANSACTION_AMOUNT_2 = 1987.47
        private val USD_TRANSACTION_AMOUNT_2_EUR = CoreUtils.roundDouble(USD_TRANSACTION_AMOUNT_2 *EXCHANGE_USD_EUR)
        val USD_TRANSACTION_AMOUNT_EUR = USD_TRANSACTION_AMOUNT_1_EUR + USD_TRANSACTION_AMOUNT_2_EUR
    }

    private var isSuccess = true

    override suspend fun getTransactions(): Flow<Result<List<TransactionModel>>> {
        return flow {
            if (isSuccess) emit(successResponse()) else emit(failureResponse())
        }
    }

    private fun successResponse(): Result<List<TransactionModel>> {
        val list = mutableListOf<TransactionModel>()
        list.add(
            TransactionModel(
                sku = EUR_TRANSACTION,
                amount = EUR_TRANSACTION_AMOUNT_1,
                currency = "EUR"
            )
        )
        list.add(
            TransactionModel(
                sku = EUR_TRANSACTION,
                amount = EUR_TRANSACTION_AMOUNT_2,
                currency = "EUR"
            )
        )

        list.add(
            TransactionModel(
                sku = CAD_TRANSACTION,
                amount = CAD_TRANSACTION_AMOUNT_1,
                currency = "CAD"
            )
        )

        list.add(
            TransactionModel(
                sku = CAD_TRANSACTION,
                amount = CAD_TRANSACTION_AMOUNT_2,
                currency = "CAD"
            )
        )

        list.add(
            TransactionModel(
                sku = USD_TRANSACTION,
                amount = USD_TRANSACTION_AMOUNT_1,
                currency = "USD"
            )
        )
        list.add(
            TransactionModel(
                sku = USD_TRANSACTION,
                amount = USD_TRANSACTION_AMOUNT_2,
                currency = "USD"
            )
        )

        return Result.Success(list)
    }

    private fun failureResponse(): Result<List<TransactionModel>> {
        return Result.Error(CodeErrors.DATA_ERROR)
    }

    fun setIsSuccessResponse(success: Boolean) {
        isSuccess = success
    }
}