package com.mperezc.data.api

import com.mperezc.core.middleware.IMiddlewareService
import com.mperezc.core.middleware.RequestResult
import retrofit2.Response

class TradesConnectorService(
    private val middlewareService: IMiddlewareService
) {

    fun <T> createApi(clazz: Class<T>): T {
        return middlewareService.createApi(ApiConstants.BASE_URL, clazz)
    }

    suspend fun <T> getResponse(
        request: suspend () -> Response<T>
    ): RequestResult<T> {
        return middlewareService.getResponse(request)
    }
}