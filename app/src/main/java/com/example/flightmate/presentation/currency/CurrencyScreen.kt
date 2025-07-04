package com.example.flightmate.presentation.currency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flightmate.domain.exception.AppException
import com.example.flightmate.presentation.common.component.ApiErrorContent
import com.example.flightmate.presentation.common.component.NetworkErrorContent
import com.example.flightmate.presentation.common.component.UnknownErrorContent
import com.example.flightmate.presentation.currency.component.CurrencyCard
import com.example.flightmate.presentation.currency.component.CurrencyInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyScreen(
    viewModel: CurrencyViewModel = hiltViewModel()
) {
    val inputState by viewModel.inputState.collectAsState()
    val currencyUiModel by viewModel.currencyUiModel.collectAsState()
    val currencyList by viewModel.currencyList.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("幣別換算") }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .padding(padding)
        ) {
            if (uiState is CurrencyUiState.Error) {
                CurrencyErrorContent(uiState) {
                    viewModel.loadCurrencies()
                }
            } else {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CurrencyInput(
                            state = inputState,
                            currencyList = currencyList,
                            onValueChange = { amountText ->
                                viewModel.updateAmount(amountText)
                            },
                            onCurrencyChange = { currency ->
                                viewModel.updateBaseCurrency(currency)
                            }
                        )
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(currencyUiModel.convertedList) { convertedCurrency ->
                                CurrencyCard(currency = convertedCurrency)
                            }
                        }
                    }
                    if (uiState is CurrencyUiState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .width(48.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencyErrorContent(uiState: CurrencyUiState, action: () -> Unit) {
    val error = (uiState as CurrencyUiState.Error).error
    when (error) {
        is AppException.ApiError,
        is AppException.HttpError -> {
            ApiErrorContent(action)
        }

        is AppException.NetworkError -> {
            NetworkErrorContent(action)
        }

        is AppException.UnknownError -> {
            UnknownErrorContent(action)
        }
    }
}
