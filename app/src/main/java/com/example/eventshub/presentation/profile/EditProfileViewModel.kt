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
    var phoneError by mutableStateOf<String?>(null)
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
                validatePhone(phone) // Validate loaded phone number
            } else {
                error = result.message
            }
            isLoading = false
        }
    }

    fun validatePhone(phone: String) {
        phoneError = when {
            phone.isBlank() -> "Phone number cannot be empty"
            !phone.matches(Regex("\\d{10}")) -> "Phone number must be exactly 10 digits"
            else -> null
        }
    }

    fun updateUser() {
        if (token.isBlank()) {
            error = "Invalid user session"
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
        validatePhone(phone) // Ensure phone is validated
        return when {
            name.isBlank() -> {
                error = "Name cannot be empty"
                false
            }
            phoneError != null -> {
                error = phoneError
                false
            }
            else -> {
                error = null
                true
            }
        }
    }
}