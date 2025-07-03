package com.example.flightmate.presentation.flight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightmate.domain.exception.AppException
import com.example.flightmate.domain.model.flight.FlightInfo
import com.example.flightmate.domain.model.flight.FlightFilter
import com.example.flightmate.domain.model.flight.FlightStatus
import com.example.flightmate.domain.usecase.GetFlightListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(
    private val getFlightListUseCase: GetFlightListUseCase,
) : ViewModel() {

    private val _allFlightList = MutableStateFlow<List<FlightInfo>>(emptyList())
    private val _filter = MutableStateFlow(FlightFilter())
    val filter = _filter.asStateFlow()

    var error by mutableStateOf<String?>(null)
        private set

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

    init {
        loadFlights()
    }

    fun loadFlights() {
        viewModelScope.launch {
            val result = getFlightListUseCase.invoke()
            result.onSuccess { flightList ->
                _allFlightList.value = flightList
            }.onFailure { error ->

                when (error) {
                    // TODO
                    is AppException.HttpError -> {}

                    is AppException.NetworkError -> {}

                    is AppException.ApiError -> {}

                    is AppException.UnknownError -> {}
                }
            }
        }
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
}
