package com.mperezc.domain.usecases

import com.mperezc.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow

interface ProductUseCase {

    companion object {
        const val DEFAULT_CURRENCY = "EUR"
    }

    suspend fun getProducts(): Flow<Result<List<ProductModel>>>

    suspend fun getProductByName(sku: String): Flow<Result<List<ProductModel>>>
}