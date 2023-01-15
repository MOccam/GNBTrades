package com.mperezc.domain.usecases

import com.mperezc.domain.model.RateMapModel
import kotlinx.coroutines.flow.Flow

interface RateUseCase {

    suspend fun getRate(): Flow<Result<RateMapModel>>
}