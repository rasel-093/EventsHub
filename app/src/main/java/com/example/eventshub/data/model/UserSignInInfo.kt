package com.example.eventshub.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserSignInInfo(
    val email: String,
    val password: String
)
