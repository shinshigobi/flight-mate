package com.example.flightmate.domain.usecase.currency

interface GetExchangeRateUseCase {
    suspend operator fun invoke(
        apiKey: String,
        baseCurrency: String?,
        currencies: String?
    ): Result<Map<String, Double>>
}
