package com.example.flightmate.data.remote

import com.example.flightmate.data.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("v1/latest")
    suspend fun getExchangeRateData(
        @Query("apikey") apiKey: String,
        @Query("base_currency") baseCurrency: String?,
        @Query("currencies") currencies: String?
    ): Response<CurrencyResponse>
}
