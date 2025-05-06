package com.example.eventshub.presentation.messages
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.data.model.Message
import com.example.eventshub.data.model.MessageRequestInfo
import com.example.eventshub.domain.repository.MessageRepository
import com.example.eventshub.util.Resource
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: MessageRepository,
    private val preferences: SharedPreferences
) : ViewModel() {
    var messages = mutableStateOf<List<Message>>(emptyList())
    var isLoading = mutableStateOf(false)
    var error = mutableStateOf<String?>(null)
    var success = mutableStateOf<String?>(null)

    fun loadMessages(receiverId: Long) {
        val token = preferences.getString("token", "") ?: ""
        val senderId = preferences.getLong("userId", -1)
        if (token.isBlank() || senderId == -1L) {
            error.value = "Authentication error."
            return
        }

        viewModelScope.launch {
            isLoading.value = true

            try {
                val result = repository.getMessages(token, MessageRequestInfo(receiverId, senderId))
                messages.value = result.data ?: emptyList()
                //Log.e("ChatViewModel", "Error loading messages ${result.data}")
            } catch (e: Exception) {
                //Log.e("ChatViewModel", "Error loading messages ${e.localizedMessage}")
                error.value = e.localizedMessage ?: "Unexpected error"
            } finally {
                isLoading.value = false
            }
        }
    }



    fun sendMessage(receiverId: Long, text: String) {
        val token = preferences.getString("token", "") ?: ""
        val senderId = preferences.getLong("userId", -1)
        Log.d("ChatViewModel", "sendMessage: $token $senderId $receiverId $text")
        if (token.isBlank() || senderId == -1L || text.isBlank()) return

        val message = Message(
            senderId = senderId,
            receiverId = receiverId,
            text = text,
            sentAt = System.currentTimeMillis()
        )
        Log.d("SEND_MESSAGE", "Sending message: $message")
        Log.d("SEND_MESSAGE", "Token: Bearer $token")

        viewModelScope.launch {
            val result = repository.sendMessage(token, message)
            Log.d("ChatViewModel", "sendMessage: ${result.message}")
            if (result is Resource.Success) {
                success.value = result.data
                loadMessages(receiverId)
            } else {
                error.value = result.message
            }
        }
    }
}
