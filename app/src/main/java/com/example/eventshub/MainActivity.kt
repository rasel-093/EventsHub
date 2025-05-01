package com.example.eventshub

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eventshub.data.model.Event
import com.example.eventshub.data.model.Role
import com.example.eventshub.data.model.Service
import com.example.eventshub.navigations.BottomNavigationBar
import com.example.eventshub.navigations.currentRoute
import com.example.eventshub.presentation.auth.signin.SignInScreen
import com.example.eventshub.presentation.auth.signup.SignUpScreen
import com.example.eventshub.presentation.events.eventdetails.EventDetailScreen
import com.example.eventshub.presentation.events.EventsScreen
import com.example.eventshub.presentation.events.EventsViewModel
import com.example.eventshub.presentation.home.HomeScreen
import com.example.eventshub.presentation.home.detail.ServiceDetailScreen
import com.example.eventshub.presentation.messages.ChatScreen
import com.example.eventshub.presentation.messages.MessageScreen
import com.example.eventshub.presentation.profile.EditProfileScreen
import com.example.eventshub.presentation.profile.ProfileScreen
import com.example.eventshub.presentation.serviceprovider.ServiceProviderHomeScreen
import com.example.eventshub.screens.ForgotPasswordScreen
import com.example.eventshub.screens.VerificationScreen
import com.example.eventshub.ui.theme.EventsHubTheme
import com.google.gson.Gson
import org.koin.androidx.compose.koinViewModel
import java.net.URLDecoder


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
    val role = preferences.getString("role", null)
    Log.d("Role", "Role is null")
    val isLoggedIn = token != null && userId != -1L && role != null
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "main" else "signin"
    ) {
        composable("signin") { SignInScreen(navController) }
        composable("signup") { SignUpScreen(navController) }
        composable("forgotpass") { ForgotPasswordScreen(navController) }
        composable("verification") { VerificationScreen(navController) }
        composable("main") {
            if (role != null) {
                MainScreen(navController, role = role)
            }
        } // Main with BottomNavigation
    }
}
@Composable
fun MainScreen(navController: NavHostController, viewModel: EventsViewModel = koinViewModel(), role: String) {
    val tabNavController = rememberNavController()

    //Conditional bottom bar rendering
    val currentRoute = currentRoute(tabNavController)
    val shouldShowBottomBar = remember(currentRoute) {
        when {
            currentRoute?.startsWith("main") == true -> true
            currentRoute?.startsWith("events") == true -> true
            currentRoute?.startsWith("messages") == true -> true
            currentRoute?.startsWith("profile") == true -> true
            currentRoute?.startsWith("home") == true -> true
           // currentRoute?.startsWith("serviceproviderhome") == true -> true
           // currentRoute?.startsWith("home") == true -> true
            else -> false
        }
    }
    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) BottomNavigationBar(tabNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = tabNavController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            //Bottom navigation composables
            composable("home") {
                Log.d("Role", role)
                if (role == Role.SERVICE_PROVIDER.toString()) {
                    Log.d("MainScreen", "Service provider home screen called")
                    ServiceProviderHomeScreen()
                } else {
                    Log.d("MainScreen", "Home screen called")
                    HomeScreen(innerPadding, tabNavController)
                }
            }
            //composable("serviceproviderhome") { ServiceProviderHomeScreen() }
            composable("events") { EventsScreen(innerPadding, tabNavController) }
            composable("messages") { MessageScreen(tabNavController) }
            composable("profile") { ProfileScreen(innerPadding, navController, tabNavController = tabNavController) }

            /** Sub Screens inside tabs */
            composable(
                route = "chat/{organizerId}",
                arguments = listOf(navArgument("organizerId") { type = NavType.IntType })
            ) { backStackEntry ->
                val organizerId = backStackEntry.arguments?.getInt("organizerId") ?: -1
                //ChatScreen(organizerList[organizerId], innerPadding, onBackClick = { navController.navigateUp()})
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
                    onBack = { tabNavController.navigateUp() }
                )
            }

            composable(
                route = "servicedetails/{serviceJson}",
                arguments = listOf(navArgument("serviceJson") { type = NavType.StringType })
            ) { backStackEntry ->
                val json = backStackEntry.arguments?.getString("serviceJson") ?: ""
                val service = Gson().fromJson(URLDecoder.decode(json, "UTF-8"), Service::class.java)
                ServiceDetailScreen(service = service, navController = tabNavController)
            }


            composable(
                route = "eventdetails/{eventJson}",
                arguments = listOf(navArgument("eventJson") { type = NavType.StringType }))
            {backStackEntry ->
                val json = backStackEntry.arguments?.getString("eventJson") ?: ""
                val event = Gson().fromJson(URLDecoder.decode(json, "UTF-8"), Event::class.java)
                if (event != null) {
                    EventDetailScreen(event, tabNavController)
                }
            }
            composable("messages") {
                MessageScreen(tabNavController)
            }

            composable(
                route = "chat/{receiverId}/{receiverName}",
                arguments = listOf(
                    navArgument("receiverId") { type = NavType.LongType },
                    navArgument("receiverName") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val receiverId = backStackEntry.arguments?.getLong("receiverId") ?: -1L
                val receiverName = backStackEntry.arguments?.getString("receiverName") ?: "Chat"
                ChatScreen(receiverId = receiverId, receiverName = receiverName)
            }

        }
    }
}
