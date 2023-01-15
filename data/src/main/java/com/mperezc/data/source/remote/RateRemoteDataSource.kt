package com.mperezc.data.source.remote

import com.mperezc.core.middleware.RequestResult
import com.mperezc.data.api.RateApi
import com.mperezc.data.api.TradesConnectorService
import com.mperezc.data.api.model.RateApiModel
import com.mperezc.data.source.RateDataSource

class RateRemoteDataSource(
    private val tradesConnectorService: TradesConnectorService
): RateDataSource {

    private lateinit var api: RateApi

    init {
        api = tradesConnectorService.createApi(RateApi::class.java)
    }

    override suspend fun getRates(): RequestResult<List<RateApiModel>> {
        return tradesConnectorService.getResponse(
            request = { api.getRates() }
        )
    }

}