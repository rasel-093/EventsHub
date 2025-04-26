package com.example.eventshub

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eventshub.navigations.BottomNavigationBar
import com.example.eventshub.presentation.auth.signin.SignInScreen
import com.example.eventshub.presentation.auth.signup.SignUpScreen
import com.example.eventshub.presentation.events.EventDetailScreen
import com.example.eventshub.presentation.events.EventsScreen
import com.example.eventshub.presentation.home.HomeScreen
import com.example.eventshub.presentation.home.detail.ServiceDetailScreen
import com.example.eventshub.presentation.profile.EditProfileScreen
import com.example.eventshub.presentation.profile.ProfileScreen
import com.example.eventshub.screens.ForgotPasswordScreen
import com.example.eventshub.screens.VerificationScreen
import com.example.eventshub.screens.getDummyOrganizers
import com.example.eventshub.screens.navscreens.MessageScreen
import com.example.eventshub.screens.navsubscreens.ChatScreen
import com.example.eventshub.ui.theme.EventsHubTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventsHubTheme {
                App()
            }
        }
    }
}
@Composable
fun App() {

    val navController = rememberNavController()
    val context = LocalContext.current
    val preferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    val token = preferences.getString("token", null)
    val userId = preferences.getLong("userId", -1)

    val isLoggedIn = token != null && userId != -1L

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "main" else "signin"
    ) {
        composable("signin") { SignInScreen(navController) }
        composable("signup") { SignUpScreen(navController) }
        composable("forgotpass") { ForgotPasswordScreen(navController) }
        composable("verification") { VerificationScreen(navController) }
        composable("main") { MainScreen(navController) } // Main with BottomNavigation
    }
}
@Composable
fun MainScreen(navController: NavHostController) {
    val tabNavController = rememberNavController()
    val organizerList = getDummyOrganizers()
    Scaffold(
        bottomBar = { BottomNavigationBar(tabNavController) }
    ) { innerPadding ->
        NavHost(
            navController = tabNavController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(innerPadding, tabNavController) }
            composable("events") { EventsScreen({}, innerPadding, tabNavController) }
            composable("messages") { MessageScreen(organizers = organizerList,{}, innerPadding) }
            composable("profile") { ProfileScreen(innerPadding, navController, tabNavController = tabNavController) }

            /** Sub Screens inside tabs */
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
                        tabNavController.navigate("profile") {
                            popUpTo("profile") { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onBack = { navController.navigateUp() }
                )
            }

            composable(
                route = "servicedetails/{serviceId}",
                arguments = listOf(navArgument("serviceId") {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                val serviceId = backStackEntry.arguments?.getInt("serviceId") ?: -1
                ServiceDetailScreen(serviceId = serviceId.toLong())

            }

            composable("eventdetails/{eventId}", arguments = listOf(navArgument("eventId") {
                type = NavType.LongType
            })) {
                val eventId = it.arguments?.getLong("eventId") ?: -1
                EventDetailScreen(eventId, navController)
            }
        }
    }
}
