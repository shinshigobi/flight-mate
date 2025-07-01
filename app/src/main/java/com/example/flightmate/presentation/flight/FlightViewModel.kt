package com.example.flightmate.presentation.flight

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightmate.data.model.FlightInfo
import com.example.flightmate.data.repository.flight.FlightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(
    private val repository: FlightRepository
) : ViewModel() {

    var flightList by mutableStateOf<List<FlightInfo>>(emptyList())
        private set

    var error by mutableStateOf<String?>(null)
        private set

    init {
        loadFlights()
    }

    fun loadFlights() {
        viewModelScope.launch {
            try {
                val response = repository.getFlightInfo()

                if (response.isSuccessful) {
                    flightList = response.body()?.instantSchedule ?: emptyList()
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
}
