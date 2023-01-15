package com.mperezc.data.api

import com.mperezc.data.api.model.TransactionApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface TransactionApi {

    @Headers("Accept: application/json")
    @GET(ApiConstants.TRANSACTION_ENDPOINT)
    suspend fun getTransactions(): Response<List<TransactionApiModel>>
}