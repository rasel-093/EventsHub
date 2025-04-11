package com.example.eventshub.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun SmallText(text: String, color: Color) {
    Text(text, color = color)
}