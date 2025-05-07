package com.example.eventshub.data.model

data class ServiceRatingInfo(
    val serviceId: Long,
    val userId: Long,
    val rating: Int,
    val feedback: String
)
