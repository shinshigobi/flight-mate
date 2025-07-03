package com.example.flightmate.data.repository.flight

import com.example.flightmate.domain.model.flight.FlightInfo

interface FlightRepository {
    suspend fun getFlightInfo(): Result<List<FlightInfo>>
}
