package com.example.flightmate.presentation.common.navigation

sealed class Screen(val route: String) {
    data object Flight: Screen("flight")
    data object Currency: Screen("currency")
}
