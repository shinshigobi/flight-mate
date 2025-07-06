package com.example.flightmate.presentation.currency

import com.example.flightmate.domain.exception.AppException
import com.example.flightmate.domain.usecase.currency.GetExchangeRateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyViewModelTest {

    private lateinit var viewModel: CurrencyViewModel
    private lateinit var fakeUseCase: FakeGetExchangeRateUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeUseCase = FakeGetExchangeRateUseCase()
        viewModel = CurrencyViewModel(fakeUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun should_load_currency_list_correctly_when_api_returns_success() = runTest {
        // Arrange
        val expected = listOf("USD", "JPY", "TWD", "EUR")
        val job = launch { viewModel.currencyList.collect {} }

        // Act
        viewModel.loadCurrencies("USD")
        advanceUntilIdle()

        // Assert
        val result = viewModel.currencyList.value
        assertEquals(expected, result)

        // Tear down
        job.cancel()
    }

    @Test
    fun should_change_base_currency_when_new_currency_is_selected() = runTest {
        // Arrange
        val expected = "EUR"

        // Act
        viewModel.updateBaseCurrency("EUR")
        advanceUntilIdle()

        // Assert
        val result = viewModel.inputState.value.baseCurrency
        assertEquals(expected, result)
    }

    @Test
    fun should_change_input_amount_when_new_amount_is_entered() = runTest {
        // Arrange
        val expected = 123.45

        // Act
        viewModel.updateAmount("123.45")
        advanceUntilIdle()

        // Assert
        val result = viewModel.inputState.value.inputAmount
        assertEquals(expected, result, 0.1)
    }

    @Test
    fun should_set_success_ui_state_when_currency_data_is_loaded_successfully() = runTest {
        // Arrange
        fakeUseCase.isApiFailed = false

        // Act
        viewModel.loadCurrencies("USD")
        advanceUntilIdle()

        // Assert
        assertTrue(viewModel.uiState.value is CurrencyUiState.Success)
    }

    @Test
    fun should_set_error_ui_state_when_currency_data_is_loaded_unsuccessfully() = runTest {
        // Arrange
        fakeUseCase.isApiFailed = true

        // Act
        viewModel.loadCurrencies("USD")
        advanceUntilIdle()

        // Assert
        assertTrue(viewModel.uiState.value is CurrencyUiState.Error)
    }
}

class FakeGetExchangeRateUseCase : GetExchangeRateUseCase {
    var isApiFailed = false

    override suspend fun invoke(baseCurrency: String?, currencies: String?): Result<Map<String, Double>> {
        return if (isApiFailed) {
            Result.failure(AppException.NetworkError)
        } else {
            Result.success(
                mapOf("USD" to 50.0, "JPY" to 150.0, "TWD" to 30.0, "EUR" to 0.9)
            )
        }
    }
}
