package com.example.eventshub.data.model

data class BookingUpdateInfo(
    val id: Long,
    val organizerId: Long,
    val status: BookingStatus
)


