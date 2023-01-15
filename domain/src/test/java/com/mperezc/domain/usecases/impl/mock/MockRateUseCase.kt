package com.mperezc.domain.usecases.impl.mock

import com.mperezc.domain.model.RateMapModel
import com.mperezc.domain.usecases.CodeErrors
import com.mperezc.domain.usecases.RateUseCase
import com.mperezc.domain.usecases.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockRateUseCase: RateUseCase {

    companion object {
        const val EXCHANGE_USD_EUR = 0.5
        const val EXCHANGE_CAD_EUR = 4.0
    }

    private var isSuccess = true

    override suspend fun getRate(): Flow<Result<RateMapModel>> {
       return flow {
           if (isSuccess) emit(successResponse()) else emit(failureResponse())
       }
    }

    private fun successResponse(): Result<RateMapModel> {
        //Crate RateMapModel
        val rateMap = mutableMapOf<Pair<String, String>, Double>()
        rateMap[Pair("USD", "EUR")] = EXCHANGE_USD_EUR
        rateMap[Pair("CAD", "EUR")] = EXCHANGE_CAD_EUR

        val rateMapModel = RateMapModel(
            rateMap,
            System.currentTimeMillis()
        )

        return Result.Success(rateMapModel)
    }

    private fun failureResponse(): Result<RateMapModel> {
        return Result.Error(CodeErrors.DATA_ERROR)
    }

    fun setIsSuccessResponse(success: Boolean) {
        isSuccess = success
    }

}