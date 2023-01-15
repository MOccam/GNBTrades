package com.mperezc.core.middleware

import retrofit2.Response

interface IMiddlewareService {

    /**
     * Create Api indicated class
     */
    fun <T> createApi(urlBase: String, clazz: Class<T>): T

    /**
     *  Return response into [RequestResult]
     * @param request Request
     * @return [RequestResult] with result of request
     */
    suspend fun <T> getResponse(
        request: suspend () -> Response<T>
    ): RequestResult<T>
}