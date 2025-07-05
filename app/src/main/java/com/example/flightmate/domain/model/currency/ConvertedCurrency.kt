package com.example.flightmate.domain.model.currency

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

/**
 * 轉換後的貨幣資料。
 *
 * @param code 貨幣代碼。
 * @param exchangeRate 匯率。
 * @param convertedAmount 轉換後的金額。
 */
data class ConvertedCurrency(
    val code: String,
    val exchangeRate: Double,
    val convertedAmount: Double,
) {
    val exchangeRateDisplay: String
        get() = exchangeRate.toExchangeRateDisplay()

    val convertedAmountDisplay: String
        get() = convertedAmount.toCurrencyDisplay()

    // 將匯率格式化取小數點後三位且最多三位
    private fun Double.toExchangeRateDisplay(): String {
        val format = DecimalFormat("#.###") //
        return format.format(this)
    }

    // 將金額格式化成千分位，顯示小數點後三位
    private fun Double.toCurrencyDisplay(): String {
        val format = NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = 3
            maximumFractionDigits = 3
        }
        return format.format(this)
    }
}
