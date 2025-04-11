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
            currentRoute?.startsWith("chat") == true -> false
            currentRoute?.startsWith("editprofile") == true -> false
            else -> true
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
