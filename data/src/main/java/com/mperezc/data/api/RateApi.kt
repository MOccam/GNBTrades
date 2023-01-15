package com.mperezc.data.api

import com.mperezc.data.api.model.RateApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface RateApi {

    @Headers("Accept: application/json")
    @GET(ApiConstants.RATES_ENDPOINT)
    suspend fun getRates(): Response<List<RateApiModel>>
}