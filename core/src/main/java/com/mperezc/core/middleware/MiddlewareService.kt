package com.mperezc.core.middleware

import com.google.gson.stream.MalformedJsonException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.net.UnknownHostException

class MiddlewareService(
    private val retrofitConfig: RetrofitConfig,
) : IMiddlewareService {

    override fun <T> createApi(urlBase: String, clazz: Class<T>): T {
        return retrofitConfig.getRetrofit(urlBase).create(clazz)
    }

    override suspend fun <T> getResponse(
        request: suspend () -> Response<T>
    ): RequestResult<T> {

        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                RequestResult.Success(result.body())
            } else {
                RequestResult.Error(ErrorUtils.parseError(result))
            }

        } catch (e: Throwable) {
            e.printStackTrace()

            when (e) {
                is MalformedJsonException -> {
                    RequestResult.Error(RequestError(ErrorCodes.MALFORMED_JSON_EXCEPTION.value, e.stackTraceToString()))
                }
                is UnknownHostException -> {
                    RequestResult.Error(RequestError(ErrorCodes.NO_NETWORK.value, e.stackTraceToString()))
                }
                else -> {
                    RequestResult.Error(RequestError(ErrorCodes.UNKNOWN.value, e.stackTraceToString()))
                }
            }
        }
    }
}