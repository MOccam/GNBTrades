package com.mperezc.gnbtrades.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mperezc.domain.usecases.CodeErrors
import com.mperezc.domain.usecases.ProductUseCase
import com.mperezc.domain.usecases.Result
import com.mperezc.gnbtrades.ui.Error
import com.mperezc.gnbtrades.ui.Loading
import com.mperezc.gnbtrades.ui.Success
import com.mperezc.gnbtrades.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val productUseCase: ProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun getProduct(sku: String?) {
        viewModelScope.launch {
            sku?.let { productId ->
                _uiState.emit(Loading)
                productUseCase.getProductByName(productId).collect {
                    when (it) {
                        is Result.Success -> {
                            _uiState.emit(Success(it.data))
                        }
                        is Result.Error -> {
                            _uiState.emit(Error(it.exception))
                        }
                    }
                }
            } ?: run {
                _uiState.emit(Error(CodeErrors.DATA_ERROR))
            }
        }
    }

}