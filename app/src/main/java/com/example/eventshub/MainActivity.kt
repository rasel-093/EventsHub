package com.example.eventshub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventshub.navigations.BottomNavigationScreen
import com.example.eventshub.screens.ForgotPasswordScreen
import com.example.eventshub.presentation.auth.signin.SignInScreen
import com.example.eventshub.presentation.auth.signup.SignUpScreen
import com.example.eventshub.screens.VerificationScreen
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
    NavHost(navController, startDestination = "signin") {
        composable("signin") { SignInScreen(navController) }
        composable("signup") { SignUpScreen(navController) }
        composable("forgotpass"){ ForgotPasswordScreen(navController)}
        composable("verification"){ VerificationScreen(navController) }
        composable("main") { MainScreen() }
    }
}
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    BottomNavigationScreen(navController = navController)
}