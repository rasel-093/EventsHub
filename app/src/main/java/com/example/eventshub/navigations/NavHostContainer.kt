//package com.example.eventshub.navigations
//
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavHostController
//import androidx.navigation.NavType
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.navArgument
//import com.example.eventshub.presentation.events.EventDetailScreen
//import com.example.eventshub.presentation.events.EventsScreen
//import com.example.eventshub.presentation.home.HomeScreen
//import com.example.eventshub.presentation.home.detail.ServiceDetailScreen
//import com.example.eventshub.presentation.profile.EditProfileScreen
//import com.example.eventshub.screens.getDummyOrganizers
//import com.example.eventshub.screens.navscreens.MessageScreen
//import com.example.eventshub.screens.navsubscreens.ChatScreen
//
//@Composable
//fun NavHostContainer(navController: NavHostController, innerPadding: PaddingValues) {
//    val organizerList = getDummyOrganizers()
//
//    NavHost(
//        navController = navController,
//        startDestination = "home"
//    ) {
//        // Navigation Screens
//        composable("home") {
//            HomeScreen(
//                innerPadding = innerPadding,
//                navController = navController,
//            )
//        }
//        composable("events") {
//            EventsScreen(
//                {
//                    //onCreateEventClick action
//                },
//                innerPadding,
//                navController
//            )
//        }
//
//        composable("messages") {
//            MessageScreen(
//                organizerList,
//                { organizerId ->
//                    navController.navigate("chat/$organizerId")
//                },
//                innerPadding
//            )
//        }
////        composable("profile") {
////            ProfileScreen(innerPadding, navController)
////        }
//
//
//
//        //Sub Screens
//        composable(
//            route = "chat/{organizerId}",
//            arguments = listOf(navArgument("organizerId") { type = NavType.IntType })
//        ) { backStackEntry ->
//            val organizerId = backStackEntry.arguments?.getInt("organizerId") ?: -1
//            ChatScreen(organizerList[organizerId], innerPadding, onBackClick = { navController.navigateUp()})
//        }
//
//        composable("editprofile") {
//            EditProfileScreen(
//                innerPadding = innerPadding,
//                onSave = {
//                    navController.navigate("profile") {
//                        popUpTo("profile") { inclusive = true }
//                        launchSingleTop = true
//                    }
//                },
//                onBack = { navController.navigateUp() }
//            )
//        }
//
//        composable(
//            route = "servicedetails/{serviceId}",
//            arguments = listOf(navArgument("serviceId") {
//                type = NavType.IntType
//            })
//        ) { backStackEntry ->
//            val serviceId = backStackEntry.arguments?.getInt("serviceId") ?: -1
//            ServiceDetailScreen(serviceId = serviceId.toLong())
//
//        }
//
//        composable("eventdetails/{eventId}", arguments = listOf(navArgument("eventId") {
//            type = NavType.LongType
//        })) {
//            val eventId = it.arguments?.getLong("eventId") ?: -1
//            EventDetailScreen(eventId, navController)
//        }
//
//
//    }
//}