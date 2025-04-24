package com.example.eventshub.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Service(
    val id: Long,
    val title: String,
    val description: String,
    val rating: Float,
    val serviceProviderId: Long,
    val fee: Double,
    val imageLink: String,
    val serviceType: String
)
