package com.example.eventshub

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import com.example.eventshub.presentation.booking.BookingScreen
import com.example.eventshub.presentation.events.EventsScreen
import com.example.eventshub.presentation.events.EventsViewModel
import com.example.eventshub.presentation.events.eventdetails.EventDetailScreen
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
import org.koin.compose.koinInject
import java.net.URLDecoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val preferences: SharedPreferences = koinInject()

            var token by remember { mutableStateOf(preferences.getString("token", null)) }
            var userId by remember { mutableStateOf(preferences.getLong("userId", -1)) }
            var role by remember { mutableStateOf(preferences.getString("role", null)) }

            // Listen to preference changes
            DisposableEffect(Unit) {
                val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                    if (key == "token" || key == "userId" || key == "role") {
                        token = preferences.getString("token", null)
                        userId = preferences.getLong("userId", -1)
                        role = preferences.getString("role", null)
                    }
                }
                preferences.registerOnSharedPreferenceChangeListener(listener)
                onDispose {
                    preferences.unregisterOnSharedPreferenceChangeListener(listener)
                }
            }

            EventsHubTheme {
                App(token, userId, role)
            }
        }
    }
}

@Composable
fun App(token:String?, userId: Long?, role: String?) {
    val navController = rememberNavController()
    Log.d("Role", role.toString())
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
        }
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
                if (role == Role.SERVICE_PROVIDER.toString()) {
                    ServiceProviderHomeScreen()
                } else {
                    HomeScreen(innerPadding, tabNavController)
                }
            }
            composable("events") {
                if (role == Role.SERVICE_PROVIDER.toString()) {
                    BookingScreen()
                } else {
                    EventsScreen(innerPadding, tabNavController)
                }
            }
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
                ChatScreen(receiverId = receiverId, navController =  tabNavController, receiverName = receiverName)
            }

        }
    }
}