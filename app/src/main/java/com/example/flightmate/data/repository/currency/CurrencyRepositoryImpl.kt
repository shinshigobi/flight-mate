package com.example.flightmate.data.repository.currency

import com.example.flightmate.BuildConfig
import com.example.flightmate.data.remote.CurrencyApi
import com.example.flightmate.domain.config.currency.CurrencyConfig
import com.example.flightmate.domain.exception.AppException
import com.squareup.moshi.JsonDataException
import java.io.IOException

class CurrencyRepositoryImpl(
    val api: CurrencyApi
): CurrencyRepository {
    override suspend fun getExchangeRateData(
        baseCurrency: String?,
        currencies: String?
    ): Result<Map<String, Double>> {
        return try {
            val apiKey = BuildConfig.EXCHANGE_RATE_API_KEY
            val currencies = currencies.takeIf { !it.isNullOrEmpty() } ?: CurrencyConfig.supportedCurrencies.joinToString(",")
            val response = api.getExchangeRateData(apiKey, baseCurrency, currencies)
            if (response.isSuccessful) {
                val exchangeRateMap = response.body()?.exchangeRateMap ?: emptyMap()
                Result.success(exchangeRateMap)
            } else {
                Result.failure(
                    AppException.HttpError(
                        code = response.code(),
                        errorBody = response.errorBody()?.string()
                    )
                )
            }
        } catch (e: JsonDataException) {
            Result.failure(AppException.ApiError("Invalid JSON format"))
        } catch (e: IOException) {
            Result.failure(AppException.NetworkError)
        } catch (e: Exception) {
            Result.failure(AppException.UnknownError(e))
        }
    }
}
