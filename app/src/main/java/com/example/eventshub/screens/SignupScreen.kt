package com.example.eventshub.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.eventshub.components.ButtonFullWidth
import com.example.eventshub.components.PasswordField
import com.example.eventshub.components.SmallText
import com.example.eventshub.components.TextButtonSmall
import com.example.eventshub.components.TextFieldWithIcon
import com.example.eventshub.components.TextMediumBold
import com.example.eventshub.ui.theme.textColorBlack

@Composable
fun SignUpScreen(navController: NavHostController) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
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
        TextMediumBold("Sign up", Color.Black)
        Spacer(modifier = Modifier.height(16.dp))

        TextFieldWithIcon(
            icon = painterResource(R.drawable.person64),
            hint = "Full name",
            text = fullName,
            onValueChange = {fullName = it},
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextFieldWithIcon(
            icon = painterResource(R.drawable.email64),
            hint = "abc@email.com",
            text = email,
            onValueChange = {email = it},
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Your password",
            isPasswordVisible = isPasswordVisible,
            onPasswordVisibilityToggle = {isPasswordVisible = !isPasswordVisible},
        )

        Spacer(modifier = Modifier.height(8.dp))

        //Confirm password
        PasswordField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = "Your password",
            isPasswordVisible = isConfirmPasswordVisible,
            onPasswordVisibilityToggle = {isConfirmPasswordVisible = !isConfirmPasswordVisible},
        )

        Spacer(modifier = Modifier.height(16.dp))

        ButtonFullWidth("SIGN UP") {
            navController.navigate("signin") {
                popUpTo("signup") { inclusive = true }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            SmallText("Already have an account?", textColorBlack)
            TextButtonSmall("Signin") {
                navController.navigate("signin") {
                    popUpTo("signup") { inclusive = true }
                }
            }
        }
    }
}