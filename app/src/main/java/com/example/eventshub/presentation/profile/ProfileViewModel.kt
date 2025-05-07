package com.example.eventshub.presentation.profile

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.domain.repository.UserRepository
import com.example.eventshub.util.Resource
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: UserRepository,
    private val preferences: SharedPreferences // For userId and token
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    init {
        loadUser()
    }

    private fun loadUser() {
        val userId = preferences.getLong("userId", -1)
        val token = preferences.getString("token", "") ?: ""

        if (userId == -1L || token.isBlank()) {
            _state.value = ProfileState(error = "User not authenticated")
            return
        }

        viewModelScope.launch {
            _state.value = ProfileState(isLoading = true)
            val result = repository.getUserById(userId, token)

            _state.value = when (result) {
                is Resource.Success -> ProfileState(user = result.data)
                is Resource.Error -> ProfileState(error = result.message)
                else -> ProfileState()
            }
        }
    }
}
