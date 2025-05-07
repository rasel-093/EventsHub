package com.example.eventshub.navigations

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.eventshub.R
import com.example.eventshub.ui.theme.secondaryColor

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val bottomNavigationItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Events,
        BottomNavItem.Messages,
        BottomNavItem.Profile
    )
    val currentRoute = currentRoute(navController)

    BottomAppBar(
        containerColor = Color.White
    ) {
        NavigationBar(
            containerColor = Color.White
        ) {
            bottomNavigationItems.forEach { item ->
                val isSelected = currentRoute == item.route
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = when (item) {
                                BottomNavItem.Home -> painterResource(R.drawable.home_icon)
                                BottomNavItem.Events -> painterResource(R.drawable.event_icon)
                                BottomNavItem.Messages -> painterResource(R.drawable.message_icon)
                                BottomNavItem.Profile -> painterResource(R.drawable.profile_icon)
                            },
                            contentDescription = item.title,
                            tint = if (isSelected) secondaryColor else Color.Gray // Set tint color based on selection
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = if (isSelected) secondaryColor else Color.Gray // Set text color based on selection
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = secondaryColor, // Icon color when selected
                        selectedTextColor = secondaryColor, // Text color when selected
                        indicatorColor = Color.Transparent, // Remove the shadow/elevation color
                        unselectedIconColor = Color.Gray, // Icon color when unselected
                        unselectedTextColor = Color.Gray // Text color when unselected
                    )
                )
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}