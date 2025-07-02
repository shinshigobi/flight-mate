package com.example.flightmate.presentation.flight

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightmate.data.model.FlightInfo
import com.example.flightmate.data.repository.flight.FlightRepository
import com.example.flightmate.domain.model.flight.FlightFilter
import com.example.flightmate.domain.model.flight.FlightStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(
    private val repository: FlightRepository
) : ViewModel() {

    private val _allFlightList = MutableStateFlow<List<FlightInfo>>(emptyList())
    private val _filter = MutableStateFlow(FlightFilter())
    val filter = _filter.asStateFlow()

    var error by mutableStateOf<String?>(null)
        private set

    val flightList = combine(_allFlightList, _filter) { flightList, condition ->
        flightList.filter { flight ->
            val statusMatch = condition.flightStatus.isEmpty() ||
                    condition.flightStatus.any { it.label == flight.flightStatus }
            val airlineMatch = condition.airlineCode.isEmpty() ||
                    condition.airlineCode.contains(flight.airlineCode)

            statusMatch && airlineMatch
        }
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
            try {
                val response = repository.getFlightInfo()

                if (response.isSuccessful) {
                    _allFlightList.value = response.body()?.instantSchedule ?: emptyList()
                    error = null
                } else {
                    error = "伺服器錯誤，HTTP ${response.code()}"
                }
            } catch (e: Exception) {
                Log.d("mTAG_${javaClass.simpleName}", e.toString())
                error = "取得航班資訊失敗"
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
