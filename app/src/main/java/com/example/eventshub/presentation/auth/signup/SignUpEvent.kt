package com.example.eventshub.presentation.auth.signup

import com.example.eventshub.data.model.Role

sealed class SignUpEvent {
    data class Submit(
        val name: String,
        val email: String,
        val password: String,
        val confirmPassword: String,
        val phone: String,
        val role: Role
    ) : SignUpEvent()
}
