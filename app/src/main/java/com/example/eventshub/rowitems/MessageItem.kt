package com.example.eventshub.rowitems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventshub.screens.navscreens.Message
import java.time.format.DateTimeFormatter

@Composable
fun MessageItem(message: Message) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm") // Format time as needed
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = if (message.isSentByUser) Alignment.End else Alignment.Start
    ) {
        // Message Bubble
        Text(
            text = message.text,
            modifier = Modifier
                .background(
                    if (message.isSentByUser) Color.Blue else Color.LightGray,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(8.dp),
            color = if (message.isSentByUser) Color.White else Color.Black
        )

        // Timestamp (optional)
        Text(
            text = message.timestamp.format(formatter),
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}