package com.example.flightmate.presentation.flight

import com.example.flightmate.domain.exception.AppException

sealed class FlightUiState {
    data object Loading : FlightUiState()
    data object Success : FlightUiState()
    data class Error(val error: AppException) : FlightUiState()
}
