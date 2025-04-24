package com.example.eventshub.presentation.auth.signin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.eventshub.R
import com.example.eventshub.components.ButtonFullWidth
import com.example.eventshub.components.CustomSwitch
import com.example.eventshub.components.PasswordField
import com.example.eventshub.components.SmallText
import com.example.eventshub.components.TextButtonSmall
import com.example.eventshub.components.TextFieldWithIcon
import com.example.eventshub.components.TextMediumBold
import com.example.eventshub.ui.theme.textColorBlack
import com.example.eventshub.ui.theme.textColorPrimary
import org.koin.androidx.compose.koinViewModel

//@Composable
//fun SignInScreen(navController: NavHostController) {
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var rememberMe by remember { mutableStateOf(false) }
//    var isPasswordVisible by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .background(Color.White),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        TextMediumBold("Sign in", Color.Black)
//        Spacer(modifier = Modifier.height(16.dp))
//
//        TextFieldWithIcon(
//            icon = painterResource(R.drawable.email64),
//            hint = "abc@email.com",
//            text = email
//        ) {
//            email = it
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        PasswordField(
//            value = password,
//            onValueChange = { password = it },
//            placeholder = "Your password",
//            isPasswordVisible = isPasswordVisible
//        ) {
//            isPasswordVisible = !isPasswordVisible
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                CustomSwitch(rememberMe) { rememberMe = it}
//                SmallText("Remember Me", textColorBlack)
//            }
//            TextButton(onClick = {
//                navController.navigate("forgotpass")
//            }) {
//                SmallText("Forgot Password?", textColorPrimary)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//        ButtonFullWidth("SIGN IN") {
//            navController.navigate("main") {
//                popUpTo("signin") { inclusive = true }
//            }
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            SmallText("Don't have an account?", textColorBlack)
//            TextButtonSmall("Sign up") {
//                navController.navigate("signup") {
//                    popUpTo("signin") { inclusive = true }
//                }
//            }
//        }
//    }
//}

@Composable
fun SignInScreen(navController: NavHostController, viewModel: SignInViewModel = koinViewModel()) {
    val state by viewModel.state

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextMediumBold("Sign in", Color.Black)
        Spacer(modifier = Modifier.height(16.dp))

        TextFieldWithIcon(painterResource(R.drawable.email64), "abc@email.com", email) {
            email = it
        }

        Spacer(modifier = Modifier.height(8.dp))

        PasswordField(password, { password = it }, "Your password", isPasswordVisible) {
            isPasswordVisible = !isPasswordVisible
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CustomSwitch(rememberMe) { rememberMe = it }
                SmallText("Remember Me", textColorBlack)
            }
            TextButton(onClick = { navController.navigate("forgotpass") }) {
                SmallText("Forgot Password?", textColorPrimary)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

//        if (state.error != null) {
//            Text(state.error ?: "", color = Color.Red)
//        }
//        if (state.isLoading) {
//            CircularProgressIndicator()
//        } else {
//            ButtonFullWidth("SIGN IN") {
//                viewModel.onEvent(SignInEvent.Submit(email, password, rememberMe))
//            }
//        }
//
//        if (state.isSuccess) {
//            LaunchedEffect(Unit) {
//                navController.navigate("main") {
//                    popUpTo("signin") { inclusive = true }
//                }
//            }
//        }
        ButtonFullWidth("SIGN IN") {
            //viewModel.onEvent(SignInEvent.Submit(email, password, rememberMe))
            navController.navigate("main") {
                    popUpTo("signin") { inclusive = true }
                }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            SmallText("Don't have an account?", textColorBlack)
            TextButtonSmall("Sign up") {
                navController.navigate("signup") {
                    popUpTo("signin") { inclusive = true }
                }
            }
        }
    }
}
