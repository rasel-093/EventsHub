package com.example.eventshub.data.model

data class BookingWithServiceDetails(
    val id: Long,
    val title: String,
    val description: String,
    val serviceProviderName: String?,
    val fee: Float,
    val imageLink: String?,
    val serviceType: ServiceType,
    val eventOrganizerName: String?,
    val eventName: String,
    val eventDate: Long,
    val bookingId: Long,
    val bookingStatus: BookingStatus
)
enum class BookingStatus {
    PENDING,APPROVED,REJECTED,CANCELLED
}