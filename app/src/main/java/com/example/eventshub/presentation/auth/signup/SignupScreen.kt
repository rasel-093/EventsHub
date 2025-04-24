package com.example.eventshub.presentation.auth.signup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.eventshub.components.PasswordField
import com.example.eventshub.components.TextFieldWithIcon
//
//@Composable
//fun SignUpScreen(navController: NavHostController) {
//    var fullName by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var confirmPassword by remember { mutableStateOf("") }
//    var isPasswordVisible by remember { mutableStateOf(false) }
//    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
//    var selectedRole by remember { mutableStateOf("User") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .background(Color.White),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        TextMediumBold("Sign up", Color.Black)
//        Spacer(modifier = Modifier.height(16.dp))
//
//        TextFieldWithIcon(
//            icon = painterResource(R.drawable.person64),
//            hint = "Full name",
//            text = fullName,
//            onValueChange = { fullName = it },
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        TextFieldWithIcon(
//            icon = painterResource(R.drawable.email64),
//            hint = "abc@email.com",
//            text = email,
//            onValueChange = { email = it },
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        PasswordField(
//            value = password,
//            onValueChange = { password = it },
//            placeholder = "Your password",
//            isPasswordVisible = isPasswordVisible,
//            onPasswordVisibilityToggle = { isPasswordVisible = !isPasswordVisible },
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        PasswordField(
//            value = confirmPassword,
//            onValueChange = { confirmPassword = it },
//            placeholder = "Confirm password",
//            isPasswordVisible = isConfirmPasswordVisible,
//            onPasswordVisibilityToggle = { isConfirmPasswordVisible = !isConfirmPasswordVisible },
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Role Selection
//        TextMediumBold("Select Role", Color.Black)
//        Spacer(modifier = Modifier.height(8.dp))
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center,
//        ) {
//            RadioButton(
//                selected = selectedRole == "User",
//                onClick = { selectedRole = "User" }
//            )
//            Text(text = "User")
//            Spacer(modifier = Modifier.width(16.dp))
//            RadioButton(
//                selected = selectedRole == "Service Provider",
//                onClick = { selectedRole = "Service Provider" }
//            )
//            Text(text = "Service Provider")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        ButtonFullWidth("SIGN UP") {
//            // You might want to include selectedRole in your logic
//            navController.navigate("signin") {
//                popUpTo("signup") { inclusive = true }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            SmallText("Already have an account?", textColorBlack)
//            TextButtonSmall("Signin") {
//                navController.navigate("signin") {
//                    popUpTo("signup") { inclusive = true }
//                }
//            }
//        }
//    }
//}

import androidx.compose.material3.*
import androidx.compose.runtime.*

import org.koin.androidx.compose.koinViewModel
import com.example.eventshub.data.model.Role


@Composable
fun SignUpScreen(navController: NavHostController) {
    val viewModel: SignUpViewModel = koinViewModel()
    val state by viewModel.state

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("User") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign up", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        TextFieldWithIcon(
            icon = painterResource(R.drawable.person64),
            hint = "Full name",
            text = fullName,
            onValueChange = { fullName = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextFieldWithIcon(
            icon = painterResource(R.drawable.email64),
            hint = "abc@email.com",
            text = email,
            onValueChange = { email = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextFieldWithIcon(
            icon = painterResource(R.drawable.phone64),
            hint = "Phone number",
            text = phone,
            onValueChange = { phone = it },
            //keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Password",
            isPasswordVisible = isPasswordVisible,
            onPasswordVisibilityToggle = { isPasswordVisible = !isPasswordVisible }
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = "Confirm password",
            isPasswordVisible = isConfirmPasswordVisible,
            onPasswordVisibilityToggle = { isConfirmPasswordVisible = !isConfirmPasswordVisible }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Select Role", style = MaterialTheme.typography.bodyMedium)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            RadioButton(
                selected = selectedRole == "User",
                onClick = { selectedRole = "User" }
            )
            Text("User")

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = selectedRole == "Service Provider",
                onClick = { selectedRole = "Service Provider" }
            )
            Text("Service Provider")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.error != null) {
            Text(text = state.error!!, color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(onClick = {
                viewModel.onEvent(
                    SignUpEvent.Submit(
                        name = fullName,
                        email = email,
                        password = password,
                        confirmPassword = confirmPassword,
                        phone = phone,
                        role = if (selectedRole == "User") Role.USER else Role.SERVICE_PROVIDER
                    )
                )
            }) {
                Text("SIGN UP")
            }
        }

        if (state.isSuccess) {
            LaunchedEffect(Unit) {
                navController.navigate("signin") {
                    popUpTo("signup") { inclusive = true }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Already have an account?")
            TextButton(onClick = {
                navController.navigate("signin") {
                    popUpTo("signup") { inclusive = true }
                }
            }) {
                Text("Signin")
            }
        }
    }
}
