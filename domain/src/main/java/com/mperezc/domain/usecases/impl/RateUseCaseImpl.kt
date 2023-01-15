package com.mperezc.domain.usecases.impl

import com.mperezc.core.middleware.succeeded
import com.mperezc.data.source.RateDataSource
import com.mperezc.domain.model.RateMapModel
import com.mperezc.domain.usecases.CodeErrors
import com.mperezc.domain.usecases.RateUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.mperezc.domain.usecases.Result
import com.mperezc.domain.usecases.Result.Error
import com.mperezc.domain.usecases.mapper.RateManager
import com.mperezc.domain.usecases.mapper.RateMapper.toModel
import java.lang.Exception

class RateUseCaseImpl(
    private val remoteDataSource: RateDataSource
) : RateUseCase {

    private var rateMapper: RateManager = RateManager()

    override suspend fun getRate(): Flow<Result<RateMapModel>> {
        return flow {
            val rawResult = remoteDataSource.getRates()

            if (rawResult.succeeded) {
                //Current available currency change
                val currencyChanges = rawResult.data.toModel
                if (currencyChanges.isEmpty()) {
                    emit(Error(CodeErrors.DATA_ERROR))
                } else {
                    val rateMap = rateMapper.creteCurrencyMap(currencyChanges)
                    emit(Result.Success(RateMapModel(rateMap, System.currentTimeMillis())))
                }
            } else {
                emit(Error(CodeErrors.NETWORK_ERROR))
            }
        }
    }

}