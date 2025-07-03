package com.example.flightmate.domain.model.currency

/**
 * 基準幣別的狀態。
 *
 * @param baseCurrency 基準幣別。預設為 "USD"。
 * @param inputAmount 輸入金額。預設為 1.0。
 */
data class CurrencyInputState(
    val baseCurrency: String = "USD",
    val inputAmount: Double = 1.0
)
