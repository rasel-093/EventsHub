package com.example.eventshub.presentation.messages

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.data.model.*
import com.example.eventshub.domain.repository.MessageRepository
import com.example.eventshub.util.Resource
import kotlinx.coroutines.launch

class MessageListViewModel(
    private val repository: MessageRepository,
    private val preferences: SharedPreferences
) : ViewModel() {
    var users = mutableStateOf<List<UserBasicInfo>>(emptyList())
    var isLoading = mutableStateOf(false)
    var error = mutableStateOf<String?>(null)

    fun loadConnectedUsers() {
        val token = preferences.getString("token", "") ?: ""
        val userId = preferences.getLong("userId", -1)
        if (token.isBlank() || userId == -1L) {
            error.value = "Authentication error."
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            try {
                val result = repository.getConnectedUsers(token, userId)
                users.value = result.data ?: emptyList()
                Log.d("MessageListViewModel", "${result.data}")
            } catch (e: Exception) {
                Log.e("MessageListViewModel", "Error loading connected users", e)
                error.value = e.localizedMessage ?: "Unexpected error occurred"
            } finally {
                isLoading.value = false
            }
        }

    }
}
