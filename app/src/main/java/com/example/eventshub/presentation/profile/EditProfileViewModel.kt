package com.example.eventshub.presentation.profile

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.data.model.ChangeUserInfo
import com.example.eventshub.domain.repository.UserRepository
import com.example.eventshub.util.Resource
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val repository: UserRepository,
    private val preferences: SharedPreferences
) : ViewModel() {

    var name by mutableStateOf("")
    var phone by mutableStateOf("")
    var error by mutableStateOf<String?>(null)
    var isLoading by mutableStateOf(false)
    var isSuccess by mutableStateOf(false)

    private val userId = preferences.getLong("userId", -1)
    private val token = preferences.getString("token", "") ?: ""

    fun loadUserInfo() {
        viewModelScope.launch {
            isLoading = true
            val result = repository.getUserById(userId, token)
            if (result is Resource.Success) {
                val user = result.data!!
                name = user.name
                phone = user.phone
            } else {
                error = result.message
            }
            isLoading = false
        }
    }

    fun updateUser() {
        val token = preferences.getString("token", "") ?: ""
        if (token.isBlank()) {
            return
        }
        if (!validateFields()) return
        viewModelScope.launch {
            isLoading = true
            val request = ChangeUserInfo(id = userId, name = name, phone = phone)
            val result = repository.changeUserInfo(request, token)
            isSuccess = result is Resource.Success
            error = result.message.takeIf { result is Resource.Error }
            isLoading = false
        }
    }
    private fun validateFields(): Boolean {
        return when {
            name.isBlank() -> {
                error = "Name cannot be empty"
                false
            }
            phone.isBlank() || phone.length < 10 || !phone.all { it.isDigit() } -> {
                error = "Phone number is invalid"
                false
            }
            else -> {
                error = null
                true
            }
        }
    }

}
