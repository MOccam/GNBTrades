package com.mperezc.gnbtrades.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mperezc.domain.usecases.ProductUseCase
import kotlinx.coroutines.launch
import com.mperezc.domain.usecases.Result
import com.mperezc.gnbtrades.ui.Loading
import com.mperezc.gnbtrades.ui.Success
import com.mperezc.gnbtrades.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.mperezc.gnbtrades.ui.Error

class ProductsViewModel(
    private val productUseCase: ProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        getTransactionsList()
    }

    fun getTransactionsList() {
        viewModelScope.launch {
            _uiState.emit(Loading)
            productUseCase.getProducts().collect {
                when (it) {
                    is Result.Success -> {
                        _uiState.emit(Success(it.data))
                    }
                    is Result.Error -> {
                        _uiState.emit(Error(it.exception))
                    }
                }
            }
        }
    }
}