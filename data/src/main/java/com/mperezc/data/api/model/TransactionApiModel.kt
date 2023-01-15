package com.mperezc.data.api.model

import com.google.gson.annotations.SerializedName

data class TransactionApiModel(
    @SerializedName("amount")
    val amount: Double?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("sku")
    val sku: String?
)
