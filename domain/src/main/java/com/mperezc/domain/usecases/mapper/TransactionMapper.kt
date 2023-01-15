package com.mperezc.domain.usecases.mapper

import com.mperezc.data.api.model.TransactionApiModel
import com.mperezc.domain.model.TransactionModel

object TransactionMapper {

    val TransactionApiModel.toModel
        get(): TransactionModel =
            TransactionModel(
                sku = this.sku.orEmpty(),
                amount = this.amount ?: 0.0,
                currency = this.currency.orEmpty()
            )

    val List<TransactionApiModel>?.toModel
        get(): List<TransactionModel> = this?.map { it.toModel } ?: listOf()
}