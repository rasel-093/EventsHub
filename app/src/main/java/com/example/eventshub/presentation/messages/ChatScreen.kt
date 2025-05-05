package com.example.eventshub.presentation.messages
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.eventshub.R
import com.example.eventshub.data.model.UserBasicInfo
import com.example.eventshub.data.model.Message
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    receiverId: Long,
    receiverName: String,
    navController: NavHostController,
    viewModel: ChatViewModel = koinViewModel()
) {
    val messages by viewModel.messages
    val isLoading by viewModel.isLoading
    var input by remember { mutableStateOf("") }
    Log.d("ChatScreen", "$messages, $isLoading")

    LaunchedEffect(Unit) {
        viewModel.loadMessages(receiverId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            title = { Text(receiverName) }
        )
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f).padding(8.dp),
                reverseLayout = true
            ) {
                items(messages.reversed()) { message ->
                    val isSender = message.senderId != receiverId
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalArrangement = if (isSender) Arrangement.End else Arrangement.Start
                    ) {
                        Column(modifier = Modifier
                            .background(Color(0xFFE3F2FD))
                            .padding(8.dp)) {
                            Text(message.text)
                            Text(
                                text = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(message.sentAt)),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White, CircleShape)
                        .border(1.dp, Color.LightGray, CircleShape),
                    placeholder = { Text("Type a message", color = Color.Gray) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    shape = CircleShape,
                    singleLine = true
                )
                Button(
                    onClick = {
                        if (input.isNotBlank()) {
                            viewModel.sendMessage(receiverId, input)
                            Log.d("ChatScreen", "Message sent: $input, ReceiverId: $receiverId")
                            input = ""
                        }
                    },
                    modifier = Modifier
                        .padding(4.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A8CC0) // WhatsApp green color
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send message",
                        tint = Color.White
                    )
                }
            }
        }
    }
}