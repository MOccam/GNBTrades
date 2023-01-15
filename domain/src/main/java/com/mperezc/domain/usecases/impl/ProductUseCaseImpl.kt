package com.mperezc.domain.usecases.impl

import com.mperezc.domain.model.ProductModel
import com.mperezc.domain.model.RateMapModel
import com.mperezc.domain.model.TransactionModel
import com.mperezc.domain.usecases.*
import com.mperezc.domain.usecases.ProductUseCase.Companion.DEFAULT_CURRENCY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class ProductUseCaseImpl(
    private val rateUseCase: RateUseCase,
    private val transactionUseCase: TransactionUseCase
): ProductUseCase {

    private var _productList = emptyList<ProductModel>()

    override suspend fun getProducts(): Flow<Result<List<ProductModel>>> {

        val rateFlow = rateUseCase.getRate()
        val transactionFlow = transactionUseCase.getTransactions()

        return rateFlow.combine(transactionFlow) { rateMap, transactionList ->
            if (rateMap.succeeded && transactionList.succeeded) {

                val products = buildProductList(
                    (rateMap as Result.Success).data,
                    (transactionList as Result.Success).data
                )
                _productList = products
                Result.Success(products)
            } else {
                if (
                    (rateMap is Result.Error && rateMap.exception == CodeErrors.DATA_ERROR) ||
                    (transactionList is Result.Error && transactionList.exception == CodeErrors.DATA_ERROR)
                ) {
                    Result.Error(CodeErrors.DATA_ERROR)
                } else {
                    Result.Error(CodeErrors.NETWORK_ERROR)
                }
            }

        }
    }

    override suspend fun getProductByName(sku: String): Flow<Result<List<ProductModel>>> {
        return flow {
            _productList.filter { it.sku == sku }.let {
                emit(Result.Success(it))
            }
        }
    }

    private fun buildProductList(
        rateMap: RateMapModel,
        transactions: List<TransactionModel>
    ): List<ProductModel> {
        val productsMap = mutableMapOf<String, ProductModel>()

        transactions.forEach {
            productsMap[it.sku]?.let { product ->
                product.totalAmount += getAmountOnDefaultCurrency(rateMap, it)
                product.transactions.add(
                    it.apply {
                        factor = rateMap.getChange(this.currency, DEFAULT_CURRENCY) ?: -1.0
                        amountEur = getAmountOnDefaultCurrency(rateMap, it)
                    }
                )
            } ?: run {
                productsMap[it.sku] = ProductModel(
                    sku = it.sku,
                    totalAmount = getAmountOnDefaultCurrency(rateMap, it),
                    transactions = mutableListOf(
                        it.apply {
                            factor = rateMap.getChange(this.currency, DEFAULT_CURRENCY) ?: -1.0
                            amountEur = getAmountOnDefaultCurrency(rateMap, it)
                        }
                    )
                )
            }
        }

        return productsMap.values.toList()
    }

    private fun getAmountOnDefaultCurrency(
        rateMap: RateMapModel,
        transaction: TransactionModel
    ): Double {
        return if (DEFAULT_CURRENCY == transaction.currency) transaction.amount else rateMap.applyCurrencyExchange(
            transaction.currency,
            DEFAULT_CURRENCY,
            transaction.amount
        ) ?: transaction.amount
    }
}