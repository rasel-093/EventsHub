package com.example.eventshub.components

import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.eventshub.ui.theme.textColorPrimary


@Composable
fun TextButtonSmall(text:String, onClick:()->Unit) {
    TextButton(onClick = {
        onClick()
    }) {
        SmallText(text = text, color = textColorPrimary)
    }
}