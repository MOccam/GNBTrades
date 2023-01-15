package com.mperezc.core.middleware

import retrofit2.Response
import java.io.IOException

object ErrorUtils {

    /**
     * Parse Error
     */
    fun parseError(response: Response<*>): RequestError {
        return try {
            //val jObjError = JSONObject(response.errorBody()?.string() ?: "")
            RequestError(
                status_code = response.code(),
                null,
                null
            )
        } catch (e: IOException) {
            RequestError(status_code = response.code())
        }
    }
}