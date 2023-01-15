package com.mperezc.core.middleware

sealed class RequestResult<out R>(
    val data: R?, val requestError: RequestError?
) {
    data class Success<out T>(val value: T?) :
        RequestResult<T>(
            value,
            null
        )

    data class Error(val error: RequestError?) :
        RequestResult<Nothing>(
            null,
            error
        )
}


val RequestResult<*>.succeeded
    get() = this is RequestResult.Success
