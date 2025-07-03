package com.example.flightmate.domain.usecase

import com.example.flightmate.data.repository.flight.FlightRepository
import com.example.flightmate.domain.model.flight.FlightInfo

class GetFlightListUseCaseImpl(
    private val repository: FlightRepository
) : GetFlightListUseCase {
    override suspend fun invoke(): Result<List<FlightInfo>> {
        return repository.getFlightInfo()
    }
}
