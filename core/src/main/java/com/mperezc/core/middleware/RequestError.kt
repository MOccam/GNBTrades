package com.mperezc.core.middleware

data class RequestError(
    val status_code: Int? = 0,
    val error_code: String? = null,
    val status_message: String? = null
)