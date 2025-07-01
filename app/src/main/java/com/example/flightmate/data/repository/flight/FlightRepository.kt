package com.example.flightmate.data.repository.flight

import com.example.flightmate.data.model.FlightResponse
import retrofit2.Response

interface FlightRepository {
    suspend fun getFlightInfo(): Response<FlightResponse>
}
