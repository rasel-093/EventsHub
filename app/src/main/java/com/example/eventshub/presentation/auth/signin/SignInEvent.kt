package com.example.eventshub.presentation.auth.signin

sealed class SignInEvent {
    data class Submit(val email: String, val password: String, val remember: Boolean) : SignInEvent()
}
