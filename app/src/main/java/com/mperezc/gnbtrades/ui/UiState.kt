package com.mperezc.gnbtrades.ui

import com.mperezc.domain.model.ProductModel

sealed class UiState

object Loading : UiState()
data class Success(val products: List<ProductModel>) : UiState()
data class Error(val code: Int): UiState()