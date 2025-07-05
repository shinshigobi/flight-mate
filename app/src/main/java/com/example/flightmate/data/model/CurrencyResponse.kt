package com.example.flightmate.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 匯率資訊。
 *
 * @param exchangeRateMap 基於基礎幣值的各幣值匯率。
 */
@JsonClass(generateAdapter = true)
data class CurrencyResponse(
    @Json(name = "data")
    val exchangeRateMap: Map<String, Double>
)
