package com.example.eventshub.data.model

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
enum class ServiceType {
    CATERER, DECORATOR
}