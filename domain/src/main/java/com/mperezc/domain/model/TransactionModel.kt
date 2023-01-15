package com.mperezc.domain.model

data class TransactionModel(
    val sku: String,
    val amount: Double,
    val currency: String,
    var factor: Double = 0.0,
    var amountEur: Double = 0.0
)