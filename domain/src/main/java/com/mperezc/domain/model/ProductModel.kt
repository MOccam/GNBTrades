package com.mperezc.domain.model

data class ProductModel(
    val sku: String,
    var totalAmount: Double,
    val transactions: MutableList<TransactionModel>
)
