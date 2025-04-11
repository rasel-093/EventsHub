package com.example.eventshub.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TextMediumBold(text: String, color: Color) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        color = color,
        fontSize = 24.sp
    )
}