package com.mperezc.domain.usecases

import com.mperezc.domain.model.TransactionModel
import kotlinx.coroutines.flow.Flow

interface TransactionUseCase {

    suspend fun getTransactions(): Flow<Result<List<TransactionModel>>>
}