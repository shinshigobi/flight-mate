package com.example.flightmate.presentation.flight

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flightmate.R
import com.example.flightmate.domain.exception.AppException
import com.example.flightmate.domain.model.flight.FlightInfo
import com.example.flightmate.presentation.common.component.ApiErrorContent
import com.example.flightmate.presentation.common.component.ErrorContent
import com.example.flightmate.presentation.common.component.NetworkErrorContent
import com.example.flightmate.presentation.common.component.UnknownErrorContent
import com.example.flightmate.presentation.flight.component.FlightCard
import com.example.flightmate.presentation.flight.component.FlightFilterDrawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FlightScreen(
    viewModel: FlightViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val flightList by viewModel.flightList.collectAsState()
    val filter by viewModel.filter.collectAsState()
    val airlineList by viewModel.airlineList.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            FlightFilterDrawer(
                filter = filter,
                airlineList = airlineList,
                onToggleStatus = { status ->
                    viewModel.filterByStatus(status)
                },
                onToggleAirline = { airlineCode ->
                    viewModel.filterByAirline(airlineCode)
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                FlightTopBar(
                    coroutineScope = coroutineScope,
                    drawerState = drawerState,
                    isFilterVisible = uiState is FlightUiState.Success &&
                            flightList.isNotEmpty() &&
                            !viewModel.isOriginalListEmpty
                )
            },
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight()
                    .padding(padding)
            ) {
                when (uiState) {
                    is FlightUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .width(48.dp)
                                .align(Alignment.Center)
                        )
                    }

                    is FlightUiState.Success -> {
                        if (flightList.isEmpty()) {
                            if (viewModel.isOriginalListEmpty) {
                                FlightEmptyContent {
                                    viewModel.loadFlights()
                                }
                            } else {
                                FlightFilterEmptyContent {
                                    coroutineScope.launch {
                                        drawerState.open()
                                    }
                                }
                            }
                        } else {
                            FlightList(flightList)
                        }
                    }

                    is FlightUiState.Error -> {
                        FlightErrorContent(uiState) {
                            viewModel.loadFlights()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FlightErrorContent(uiState: FlightUiState, action: () -> Unit) {
    val error = (uiState as FlightUiState.Error).error
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

@Composable
fun FlightList(flightList: List<FlightInfo>) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(flightList) { flight ->
                FlightCard(flight = flight)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightTopBar(
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    isFilterVisible: Boolean
) {
    CenterAlignedTopAppBar(
        title = { Text("航班資訊") },
        actions = {
            AnimatedVisibility(isFilterVisible) {
                IconButton(onClick = {
                    coroutineScope.launch { drawerState.open() }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_filter_list),
                        contentDescription = "篩選"
                    )
                }
            }
        }
    )
}

@Composable
fun FlightEmptyContent(buttonOnClick: () -> Unit) {
    ErrorContent(
        title = "目前沒有航班",
        message = "尚無任何可用的航班資料",
        iconResId = R.drawable.ic_sentiment_very_dissatisfied,
        buttonLabel = "重新整理",
        buttonOnClick = buttonOnClick
    )
}

@Composable
fun FlightFilterEmptyContent(buttonOnClick: () -> Unit) {
    ErrorContent(
        title = "無此篩選結果",
        message = "無法找到符合篩選條件的內容",
        iconResId = R.drawable.ic_search_off,
        buttonLabel = "重新篩選",
        buttonOnClick = buttonOnClick
    )
}
