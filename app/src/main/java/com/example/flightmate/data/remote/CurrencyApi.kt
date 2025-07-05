package com.example.flightmate.data.remote

import com.example.flightmate.data.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    /**
     * 取得匯率資訊。
     *
     * @param apiKey API 金鑰。
     * @param baseCurrency 基準幣別代碼，例如："USD"，預設為 "USD"。
     * @param currencies 欲取得的幣別代碼，以逗號分隔，例如："USD,ERU"。
     * 預設為所有可用貨幣。
     */
    @GET("v1/latest")
    suspend fun getExchangeRateData(
        @Query("apikey") apiKey: String,
        @Query("base_currency") baseCurrency: String?,
        @Query("currencies") currencies: String?
    ): Response<CurrencyResponse>
}
