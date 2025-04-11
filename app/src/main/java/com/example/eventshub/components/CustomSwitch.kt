package com.example.eventshub.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.eventshub.ui.theme.primaryColor
import com.example.eventshub.ui.theme.textColorPrimary

@Composable
fun CustomSwitch(rememberMe: Boolean, onCheckedChange: (Boolean) -> Unit) {
    // Custom colors for the switch
    val switchColors: SwitchColors = SwitchDefaults.colors(
        checkedThumbColor = primaryColor, // Thumb color when switch is ON
        uncheckedThumbColor = Color.Gray, // Thumb color when switch is OFF
        checkedTrackColor = textColorPrimary, // Track color when switch is ON
        //uncheckedTrackColor = Color.Gray, // Track color when switch is OFF
    )

    Switch(
        modifier = Modifier.padding(8.dp),
        checked = rememberMe,
        onCheckedChange = onCheckedChange,
        colors = switchColors // Apply custom colors
    )
}