package com.example.flightmate.domain.usecase

import com.example.flightmate.domain.model.flight.FlightInfo

interface GetFlightListUseCase {
    suspend operator fun invoke(): Result<List<FlightInfo>>
}
