package com.example.flightmate.presentation.common.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.flightmate.presentation.common.navigation.Screen

data class BottomNavItem(
    val screen: Screen,
    val label: String,
    @DrawableRes val iconResourceId: Int
)

@Composable
fun BottomBar(navController: NavController, items: List<BottomNavItem>) {
    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    if (currentRoute != item.screen.route) {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = { Text(item.label) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconResourceId),
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }
    }
}
