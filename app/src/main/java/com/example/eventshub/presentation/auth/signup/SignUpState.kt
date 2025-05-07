package com.example.eventshub.presentation.auth.signup

data class SignUpState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)
