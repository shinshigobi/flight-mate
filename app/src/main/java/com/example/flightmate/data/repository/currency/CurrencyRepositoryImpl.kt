package com.example.flightmate.data.repository.currency

import android.util.Log
import com.example.flightmate.data.remote.CurrencyApi
import com.example.flightmate.domain.exception.AppException
import com.squareup.moshi.JsonDataException
import java.io.IOException

class CurrencyRepositoryImpl(
    val api: CurrencyApi
): CurrencyRepository {
    override suspend fun getExchangeRateData(
        apiKey: String,
        baseCurrency: String?,
        currencies: String?
    ): Result<Map<String, Double>> {
        return try {
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
            Log.d("mTAG_${javaClass.simpleName}", e.toString())
            Result.failure(AppException.UnknownError(e))
        }
    }
}
