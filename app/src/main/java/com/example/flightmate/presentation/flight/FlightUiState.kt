package com.example.flightmate.presentation.flight

import com.example.flightmate.domain.exception.AppException

sealed class FlightUiState {
    object Loading : FlightUiState()
    object Success : FlightUiState()
    data class Error(val error: AppException) : FlightUiState()
}
