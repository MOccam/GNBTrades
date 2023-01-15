package com.mperezc.domain.usecases.mapper

import com.mperezc.data.api.model.RateApiModel
import com.mperezc.domain.model.RateModel

object RateMapper {

    val RateApiModel.toModel
        get(): RateModel =
            RateModel(
                from = this.from.orEmpty(),
                to = this.to.orEmpty(),
                rate = this.rate?: 1.0
            )

    val List<RateApiModel>?.toModel
        get(): List<RateModel> = this?.map { it.toModel } ?: listOf()
}