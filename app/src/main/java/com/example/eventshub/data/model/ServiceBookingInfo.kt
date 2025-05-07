package com.example.eventshub.data.model

data class ServiceBookingInfo(
    val serviceId:Long,
    val eventOrganizerId:Long,
    val status: BookingStatus
)