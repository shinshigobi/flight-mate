package com.example.flightmate.domain.usecase.currency

import com.example.flightmate.data.repository.currency.CurrencyRepository

class GetExchangeRateDataUseCaseImpl(
    val repository: CurrencyRepository
): GetExchangeRateUseCase {
    override suspend fun invoke(
        baseCurrency: String?,
        currencies: String?
    ): Result<Map<String, Double>> {
        return repository.getExchangeRateData(baseCurrency, currencies)
    }
}
