package com.example.flightmate.data.repository.currency

interface CurrencyRepository {
    suspend fun getExchangeRateData(
        baseCurrency: String?,
        currencies: String?
    ): Result<Map<String, Double>>
}
