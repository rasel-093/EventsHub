package com.example.eventshub.presentation.profile

import com.example.eventshub.data.model.User

data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)
