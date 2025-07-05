package com.example.flightmate.presentation.currency

import com.example.flightmate.domain.exception.AppException

sealed class CurrencyUiState {
    data object Loading : CurrencyUiState()
    data object Success : CurrencyUiState()
    data class Error(val error: AppException) : CurrencyUiState()
}
