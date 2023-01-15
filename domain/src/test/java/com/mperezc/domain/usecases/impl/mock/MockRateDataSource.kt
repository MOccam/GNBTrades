package com.mperezc.domain.usecases.impl.mock

import com.mperezc.core.middleware.ErrorCodes
import com.mperezc.core.middleware.RequestError
import com.mperezc.core.middleware.RequestResult
import com.mperezc.data.api.model.RateApiModel
import com.mperezc.data.source.RateDataSource

class MockRateDataSource: RateDataSource {

    companion object {
        const val FROM = "EUR"
        const val RATE = 0.5
        const val TO = "CAD"
    }

    private var isSuccess = true

    override suspend fun getRates(): RequestResult<List<RateApiModel>> {

        val apiModel = RateApiModel(
            from = FROM,
            rate = RATE,
            to = TO
        )

        return if (isSuccess) {
            RequestResult.Success(
                listOf(apiModel)
            )
        } else {
            RequestResult.Error(
                RequestError(ErrorCodes.UNKNOWN.value, "")
            )
        }
    }

    fun setFailure() {
        isSuccess = false
    }
}