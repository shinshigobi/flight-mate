package com.example.flightmate.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyResponse(
    @Json(name = "data")
    val exchangeRateMap: Map<String, Double>
)
