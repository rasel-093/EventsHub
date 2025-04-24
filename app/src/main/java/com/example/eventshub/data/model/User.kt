package com.example.eventshub.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long = 0,
    val name: String,
    val role: Role,
    val password: String,
    val phone: String,
    val email: String
)

enum class Role {
    USER, SERVICE_PROVIDER
}
