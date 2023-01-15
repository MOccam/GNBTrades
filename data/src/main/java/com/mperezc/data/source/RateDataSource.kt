package com.mperezc.data.source

import com.mperezc.core.middleware.RequestResult
import com.mperezc.data.api.model.RateApiModel

interface RateDataSource {

    suspend fun getRates(): RequestResult<List<RateApiModel>>
}