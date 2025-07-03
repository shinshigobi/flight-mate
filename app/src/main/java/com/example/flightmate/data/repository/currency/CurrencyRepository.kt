package com.example.flightmate.data.repository.currency

interface CurrencyRepository {
    suspend fun getExchangeRateData(
        apiKey: String,
        baseCurrency: String?,
        currencies: String?
    ): Result<Map<String, Double>>
}
