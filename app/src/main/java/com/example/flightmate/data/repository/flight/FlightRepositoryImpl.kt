package com.example.flightmate.data.repository.flight

import com.example.flightmate.data.model.FlightResponse
import com.example.flightmate.data.remote.FlightApi
import retrofit2.Response
import javax.inject.Inject

class FlightRepositoryImpl @Inject constructor(
    private val api: FlightApi
) : FlightRepository {
    override suspend fun getFlightInfo(): Response<FlightResponse> {
        return api.getFlightInfoList() // TODO try-catch 錯誤處理
    }
}
