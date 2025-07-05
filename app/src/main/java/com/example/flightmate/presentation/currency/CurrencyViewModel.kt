package com.example.flightmate.presentation.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightmate.domain.exception.AppException
import com.example.flightmate.domain.model.currency.ConvertedCurrency
import com.example.flightmate.domain.model.currency.CurrencyInputState
import com.example.flightmate.domain.usecase.currency.GetExchangeRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getCurrencyDataUseCase: GetExchangeRateUseCase
): ViewModel() {
    private val _exchangeRateMap = MutableStateFlow<Map<String, Double>>(emptyMap())

    private val _uiState = MutableStateFlow<CurrencyUiState>(CurrencyUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _inputState = MutableStateFlow(CurrencyInputState())
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
    val currencyList = _exchangeRateMap.map { exchangeRateMap ->
        exchangeRateMap.keys.toList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = listOf()
    )

    init {
        loadCurrencies()
    }

    fun loadCurrencies(
        baseCurrency: String? = inputState.value.baseCurrency
    ) {
        viewModelScope.launch {
            _uiState.value = CurrencyUiState.Loading

            val result = getCurrencyDataUseCase.invoke(
                baseCurrency,
                null
            )
            result.onSuccess { exchangeRateMap ->
                _exchangeRateMap.value = exchangeRateMap
                _uiState.value = CurrencyUiState.Success
            }.onFailure { throwable ->
                val error = throwable as? AppException ?: AppException.UnknownError(throwable)
                _uiState.value = CurrencyUiState.Error(error)
            }
        }
    }

    fun updateBaseCurrency(currencyCode: String) {
        _inputState.value = _inputState.value.copy(baseCurrency = currencyCode)
        loadCurrencies(currencyCode)
    }

    fun updateAmount(amount: String) {
        val inputAmount = amount.ifBlank { "0.0" }.toDoubleOrNull()
        inputAmount?.let {
            _inputState.value = _inputState.value.copy(inputAmount = it)
        }
    }

    private fun mapToConvertedList(
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
