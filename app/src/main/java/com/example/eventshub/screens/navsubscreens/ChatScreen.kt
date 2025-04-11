package com.example.eventshub.screens.navsubscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.eventshub.components.TopBarWithBackButton
import com.example.eventshub.rowitems.MessageItem
import com.example.eventshub.screens.navscreens.Organizer
import com.example.eventshub.ui.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(organizer: Organizer, innerPadding: PaddingValues, onBackClick: () -> Unit) {

    var messageText by remember { mutableStateOf(TextFieldValue("")) }
    //TopAppBar(title = { Text(organizer.name) })
    Scaffold(
        topBar = { TopBarWithBackButton(topBarText = organizer.name){
            //navigate back
            onBackClick()
        } }
    ) {ownPadding->
        Column(modifier = Modifier
            .padding(innerPadding)
           // .padding(ownPadding)
            .background(Color.White)
            .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // Fill available space
                contentPadding = PaddingValues(16.dp),
                reverseLayout = true // Display messages from bottom to top
            ) {
                items(organizer.messages.reversed()) { message -> // Reverse the list for display
                    MessageItem(message)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type a message...") },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = primaryColor,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = {
                    messageText = TextFieldValue("")
                }) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = "Send", tint = primaryColor)
                }
            }
        }
    }
}