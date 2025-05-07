package com.example.eventshub.presentation.auth.signin

data class SignInState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)
