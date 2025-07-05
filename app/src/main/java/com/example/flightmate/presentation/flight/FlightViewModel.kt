package com.example.flightmate.presentation.flight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightmate.domain.exception.AppException
import com.example.flightmate.domain.model.flight.FlightInfo
import com.example.flightmate.domain.model.flight.FlightFilter
import com.example.flightmate.domain.model.flight.FlightStatus
import com.example.flightmate.domain.usecase.GetFlightListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(
    private val getFlightListUseCase: GetFlightListUseCase,
) : ViewModel() {
    private val _allFlightList = MutableStateFlow<List<FlightInfo>>(emptyList())

    private val _uiState = MutableStateFlow<FlightUiState>(FlightUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _filter = MutableStateFlow(FlightFilter())
    val filter = _filter.asStateFlow()

    val isOriginalListEmpty: Boolean
        get() = _allFlightList.value.isEmpty()

    val flightList = combine(_allFlightList, _filter) { flightList, condition ->
        flightList.filter { flight ->
            val statusMatch = condition.flightStatus.isEmpty() ||
                    condition.flightStatus.contains(flight.flightStatus)
            val airlineMatch = condition.airlineCode.isEmpty() ||
                    condition.airlineCode.contains(flight.airline.code)

            statusMatch && airlineMatch
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )
    val airlineList = _allFlightList.map { flightList ->
        flightList.map { it.airline }.distinctBy { it.code }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )
    private val refreshDelay = 10_000L
    private var refreshJob: Job? = null

    // 需求：每 10 秒重新呼叫
    fun autoRefresh() {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            while (isActive) {
                val isSuccess = loadFlights()
                if (!isSuccess) break
                delay(refreshDelay)
            }
        }
    }

    fun manualRefresh() {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            val isSuccess = loadFlights()
            if (!isSuccess) return@launch
            delay(refreshDelay)
            autoRefresh()
        }
    }

    fun cancelRefresh() {
        refreshJob?.cancel()
        refreshJob = null
    }

    fun filterByStatus(status: FlightStatus) {
        val current = _filter.value.flightStatus
        val updated = if (status in current) current - status else current + status
        _filter.value = _filter.value.copy(flightStatus = updated)
    }

    fun filterByAirline(code: String) {
        val current = _filter.value.airlineCode
        val updated = if (code in current) current - code else current + code
        _filter.value = _filter.value.copy(airlineCode = updated)
    }

    private suspend fun loadFlights(): Boolean {
        _uiState.value = FlightUiState.Loading

        val result = getFlightListUseCase.invoke()
        return result.fold(
            onSuccess = { flightList ->
                _allFlightList.value = flightList
                _uiState.value = FlightUiState.Success
                true
            },
            onFailure = { error ->
                val error = error as? AppException ?: AppException.UnknownError(error)
                _uiState.value = FlightUiState.Error(error)
                false
            }
        )
    }
}
