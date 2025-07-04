package com.example.flightmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.example.flightmate.presentation.common.component.BottomBar
import com.example.flightmate.presentation.common.component.BottomNavItem
import com.example.flightmate.presentation.common.navigation.AppNavHost
import com.example.flightmate.presentation.common.navigation.Screen
import com.example.flightmate.ui.theme.FlightMateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlightMateTheme(dynamicColor = false) {
                val navController = rememberNavController()
                val items = listOf(
                    BottomNavItem(Screen.Flight, "航班", R.drawable.ic_travel),
                    BottomNavItem(Screen.Currency, "匯率", R.drawable.ic_currency_exchange)
                )

                Scaffold(
                    bottomBar = {
                        BottomBar(navController, items)
                    }
                ) { padding ->
                    AppNavHost(
                        navController = navController,
                        padding = padding
                    )
                }
            }
        }
    }
}
