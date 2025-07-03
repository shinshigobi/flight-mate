package com.example.flightmate.presentation.currency

import com.example.flightmate.domain.model.currency.ConvertedCurrency
import com.example.flightmate.domain.model.currency.CurrencyInputState

/**
 * 幣別的 UI 模型。
 *
 * @param inputState 基準幣別的狀態。
 * @param convertedList 轉換後的貨幣列表。
 */
data class CurrencyUiModel(
    val inputState: CurrencyInputState,
    val convertedList: List<ConvertedCurrency>
)
