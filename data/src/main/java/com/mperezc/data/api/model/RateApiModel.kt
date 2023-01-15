package com.mperezc.data.api.model

import com.google.gson.annotations.SerializedName

data class RateApiModel(
    @SerializedName("from")
    val from: String?,
    @SerializedName("rate")
    val rate: Double?,
    @SerializedName("to")
    val to: String?
)
