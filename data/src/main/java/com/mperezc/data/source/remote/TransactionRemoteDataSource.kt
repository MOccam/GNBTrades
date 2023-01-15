package com.mperezc.data.source.remote

import com.mperezc.core.middleware.RequestResult
import com.mperezc.data.api.TradesConnectorService
import com.mperezc.data.api.TransactionApi
import com.mperezc.data.api.model.TransactionApiModel
import com.mperezc.data.source.TransactionDataSource

class TransactionRemoteDataSource(
    private val tradesConnectorService: TradesConnectorService
): TransactionDataSource {

    private lateinit var api: TransactionApi

    init {
        api = tradesConnectorService.createApi(TransactionApi::class.java)
    }

    override suspend fun getTransactions(): RequestResult<List<TransactionApiModel>> {
        return tradesConnectorService.getResponse(
            request = { api.getTransactions() }
        )
    }
}