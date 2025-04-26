package com.example.eventshub.navigations

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationScreen(navController: NavHostController) {
    val currentRoute = currentRoute(navController)
    val shouldShowBottomBar = remember(currentRoute) {
        when {
            currentRoute?.startsWith("home") == true -> true
            currentRoute?.startsWith("events") == true -> true
            currentRoute?.startsWith("messages") == true -> true
            currentRoute?.startsWith("profile") == true -> true
            else -> false
        }
    }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHostContainer(navController = navController, innerPadding = innerPadding)
    }
}
@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
