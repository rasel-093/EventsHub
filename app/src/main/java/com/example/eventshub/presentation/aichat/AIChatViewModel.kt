package com.example.eventshub.presentation.aichat

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.data.model.ImageGenerateMessage
import com.example.eventshub.domain.repository.AIRepository
import com.example.eventshub.util.Resource
import kotlinx.coroutines.launch

class AIChatViewModel(
    private val repository: AIRepository,
    private val preferences: SharedPreferences
) : ViewModel() {
    var messages = mutableStateOf<List<ImageGenerateMessage?>>(emptyList())
    var isLoading = mutableStateOf(false)
    var error = mutableStateOf<String?>(null)
    val userId = preferences.getLong("userId", -1)

    fun loadMessages() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val result = repository.getAIMessages(userId)
                messages.value = result.data ?: emptyList()
            } catch (e: Exception) {
                Log.d("Loadmsg vm", e.toString())
                error.value = e.localizedMessage ?: "Failed to load messages"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun sendMessage(text: String) {
        val message = ImageGenerateMessage(
            id = 0,
            senderId = userId,
            text = text,
            imageLink = null
        )
        messages.value += message

        viewModelScope.launch {
            isLoading.value = true
            try {
                val result = repository.sendAIMessage(message)
                if (result is Resource.Success) {
                    messages.value = (messages.value + result.data)
                } else {
                    error.value = result.message
                }
            } catch (e: Exception) {
                error.value = e.localizedMessage ?: "Failed to send message"
            } finally {
                isLoading.value = false
            }
        }
    }
}