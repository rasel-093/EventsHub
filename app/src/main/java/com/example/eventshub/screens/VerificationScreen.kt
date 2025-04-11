package com.example.eventshub.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun VerificationScreen(navController: NavHostController) {
    var code1 by remember { mutableStateOf("") }
    var code2 by remember { mutableStateOf("") }
    var code3 by remember { mutableStateOf("") }
    var code4 by remember { mutableStateOf("") }
    var timer by remember { mutableIntStateOf(20) }

    // Simulate countdown timer
    LaunchedEffect(Unit) {
        while (timer > 0) {
            kotlinx.coroutines.delay(1000L)
            timer--
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Verification", fontSize = 24.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "We've sent you the verification code on +966 555 555 555",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            VerificationCodeInput(code1) { code1 = it }
            VerificationCodeInput(code2) { code2 = it }
            VerificationCodeInput(code3) { code3 = it }
            VerificationCodeInput(code4) { code4 = it }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle continue */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text("CONTINUE", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Re-send code in 0:${if (timer < 10) "0$timer" else timer}",
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}

@Composable
fun VerificationCodeInput(value: String, onValueChange: (String) -> Unit) {
    BasicTextField(
        value = value,
        onValueChange = { if (it.length <= 1) onValueChange(it) },
        modifier = Modifier
            .size(50.dp)
            .background(Color.LightGray, shape = MaterialTheme.shapes.small)
            .padding(16.dp),
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Text("-", color = Color.Gray)
            }
            innerTextField()
        },
        visualTransformation = VisualTransformation.None
    )
}