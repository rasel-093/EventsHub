package com.example.eventshub.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.eventshub.R
import com.example.eventshub.components.ButtonFullWidth
import com.example.eventshub.components.TextFieldWithIcon

@Composable
fun ForgotPasswordScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Forgot password?", fontSize = 32.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(16.dp))
        TextFieldWithIcon(
            icon = painterResource(R.drawable.email64),
            hint = "Enter your email address",
            text = email,
        ) {
            email = it
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "* We will send you a message to set or reset your new password",
            color = Color.Red,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        ButtonFullWidth(
            text = "CONTINUE"
        ) {
            navController.navigate("verification")
        }
    }
}