package com.example.flightmate.presentation.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightmate.domain.model.currency.ConvertedCurrency
import com.example.flightmate.domain.model.currency.CurrencyInputState
import com.example.flightmate.domain.usecase.currency.GetExchangeRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    val getCurrencyDataUseCase: GetExchangeRateUseCase
): ViewModel() {
    private val _exchangeRateMap = MutableStateFlow<Map<String, Double>>(emptyMap())

    private val _inputState = MutableStateFlow<CurrencyInputState>(CurrencyInputState())
    val inputState = _inputState.asStateFlow()

    val currencyUiModel = combine(
        _exchangeRateMap, _inputState
    ) { exchangeRateMap, inputState ->
        val list = mapToConvertedList(exchangeRateMap, inputState)
        CurrencyUiModel(inputState, list)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CurrencyUiModel(CurrencyInputState(), emptyList())
    )

    init {
        loadCurrencies()
    }

    fun loadCurrencies() {
        viewModelScope.launch {
            val result = getCurrencyDataUseCase.invoke(
                "fca_live_dSqZrcLeMaOu0fAT6mktIAgt136ymgnjd8nLifxv",
                null,
                null
            )
            result.onSuccess { exchangeRateMap ->
                _exchangeRateMap.value = exchangeRateMap
            }.onFailure {
                // TODO: Handle error
            }
        }
    }

    fun changeBaseCurrency(currencyCode: String) {
        _inputState.value = _inputState.value.copy(baseCurrency = currencyCode)
        // TODO fetch api
    }

    fun updateAmount(amount: String) {
        val inputAmount = amount.toDoubleOrNull() ?: 0.0
        _inputState.value = _inputState.value.copy(inputAmount = inputAmount)
    }

    fun mapToConvertedList(
        rateMap: Map<String, Double>,
        input: CurrencyInputState
    ): List<ConvertedCurrency> {
        return rateMap
            .filterKeys { it != input.baseCurrency }
            .map { (currencyCode, rate) ->
                ConvertedCurrency(
                    code = currencyCode,
                    exchangeRate = rate,
                    convertedAmount = input.inputAmount * rate
                )
            }
    }
}
