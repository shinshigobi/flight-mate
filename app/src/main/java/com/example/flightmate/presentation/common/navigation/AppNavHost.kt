package com.example.flightmate.presentation.common.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flightmate.presentation.currency.CurrencyScreen
import com.example.flightmate.presentation.flight.FlightScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Flight.route
    ) {
        composable(Screen.Flight.route) { FlightScreen(outerPadding = padding) }
        composable(Screen.Currency.route) { CurrencyScreen(outerPadding = padding) }
    }
}
