package com.mperezc.core.middleware

enum class ErrorCodes(val value: Int) {
    SERVICE_ERROR(404),
    NO_NETWORK(701),
    MALFORMED_JSON_EXCEPTION(702),
    UNKNOWN(799)
}