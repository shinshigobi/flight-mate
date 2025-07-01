package com.example.flightmate.data.remote

import com.example.flightmate.data.model.FlightResponse
import retrofit2.Response
import retrofit2.http.GET

interface FlightApi {
    @GET("API/InstantSchedule.ashx?AirFlyLine=2&AirFlyIO=2")
    suspend fun getFlightInfoList(): Response<FlightResponse>
}
