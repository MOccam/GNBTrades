package com.mperezc.domain.usecases.impl

import com.mperezc.core.middleware.succeeded
import com.mperezc.data.source.TransactionDataSource
import com.mperezc.domain.model.TransactionModel
import com.mperezc.domain.usecases.CodeErrors
import com.mperezc.domain.usecases.TransactionUseCase
import com.mperezc.domain.usecases.mapper.TransactionMapper.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.mperezc.domain.usecases.Result
import com.mperezc.domain.usecases.Result.Error
import com.mperezc.domain.usecases.Result.Success
import java.lang.Exception

class TransactionUseCaseImpl(
    private val remoteDataSource: TransactionDataSource
) : TransactionUseCase {

    override suspend fun getTransactions(): Flow<Result<List<TransactionModel>>> {
        return flow {
            val rawResult = remoteDataSource.getTransactions()

            if (rawResult.succeeded) {
                emit(Success(rawResult.data.toModel))
            } else {
                emit(Error(CodeErrors.NETWORK_ERROR))
            }
        }
    }
}