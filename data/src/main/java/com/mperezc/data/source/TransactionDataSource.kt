package com.mperezc.data.source

import com.mperezc.core.middleware.RequestResult
import com.mperezc.data.api.model.TransactionApiModel

interface TransactionDataSource {

    suspend fun getTransactions(): RequestResult<List<TransactionApiModel>>
}