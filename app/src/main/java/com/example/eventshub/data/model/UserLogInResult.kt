package com.example.eventshub.data.model

data class UserLogInResult(
    val userId: Long,
    val roles: String,
    val token: String
)
