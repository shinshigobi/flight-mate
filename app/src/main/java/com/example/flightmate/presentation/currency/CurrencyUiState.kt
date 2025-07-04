package com.example.flightmate.presentation.currency

import com.example.flightmate.domain.exception.AppException

sealed class CurrencyUiState {
    object Loading : CurrencyUiState()
    object Success : CurrencyUiState()
    data class Error(val error: AppException) : CurrencyUiState()
}
