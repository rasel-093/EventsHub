package com.example.eventshub.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserLogInResult(
    val userId: Long,
    val roles: String,
    val token: String
)
