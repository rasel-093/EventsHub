package com.example.eventshub.navigations

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.eventshub.screens.getDummyOrganizers
import com.example.eventshub.screens.getDummyPastEvents
import com.example.eventshub.screens.getDummyUpcomingEvents
import com.example.eventshub.screens.navscreens.EventsScreen
import com.example.eventshub.screens.navscreens.HomeScreen
import com.example.eventshub.screens.navscreens.MessageScreen
import com.example.eventshub.screens.navscreens.ProfileScreen
import com.example.eventshub.screens.navsubscreens.ChatScreen
import com.example.eventshub.screens.navsubscreens.EditProfileScreen
import com.example.eventshub.screens.navsubscreens.EventDetailScreen

@Composable
fun NavHostContainer(navController: NavHostController, innerPadding: PaddingValues) {
    val eventList = getDummyUpcomingEvents()
    val organizerList = getDummyOrganizers()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Navigation Screens
        composable("home") {
            HomeScreen(innerPadding)
        }
        composable("events") {
            EventsScreen(
                getDummyUpcomingEvents(),
                getDummyPastEvents(),
                {},
                innerPadding,
                navController
            )
        }
        composable("messages") {
            MessageScreen(
                organizerList,
                { organizerId ->
                    navController.navigate("chat/$organizerId")
                },
                innerPadding
            )
        }
        composable("profile") {
            ProfileScreen(innerPadding, navController)
        }



        //Sub Screens
        composable(
            route = "chat/{organizerId}",
            arguments = listOf(navArgument("organizerId") { type = NavType.IntType })
        ) { backStackEntry ->
            val organizerId = backStackEntry.arguments?.getInt("organizerId") ?: -1
            ChatScreen(organizerList[organizerId], innerPadding, onBackClick = { navController.navigateUp()})
        }

        composable("editprofile") {
            EditProfileScreen(
                innerPadding = innerPadding,
                onSave = {
                    navController.navigate("profile") {
                        popUpTo("profile") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onBack = { navController.navigateUp() }
            )
        }
        composable("eventdetails"){
            EventDetailScreen(
                event = eventList[0]
            ) {
                navController.navigateUp()
            }
        }
    }
}