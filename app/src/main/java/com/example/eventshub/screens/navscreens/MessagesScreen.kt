package com.example.eventshub.screens.navscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventshub.rowitems.MessageCard
import com.example.eventshub.ui.theme.primaryColor
import java.time.LocalDateTime
data class Organizer(
    val id: Int,
    val name: String,
    val profileImage: Int, // Resource ID
    val messages: List<Message>
)
data class Message(val text: String, val isSentByUser: Boolean, val timestamp: LocalDateTime)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(organizers: List<Organizer>, onOrganizerSelected: (Int) -> Unit, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(innerPadding)
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "EventsHub",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor)
        LazyColumn(modifier = Modifier
            .fillMaxSize()
        ) {
            itemsIndexed(organizers) {index, organizer ->
                MessageCard(organizer) { onOrganizerSelected(index) }
            }
        }
    }
}