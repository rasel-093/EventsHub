package com.example.eventshub.domain.repository

import com.example.eventshub.data.model.BookingUpdateInfo
import com.example.eventshub.data.model.BookingWithServiceDetails
import com.example.eventshub.data.model.ServiceBookingInfo
import com.example.eventshub.util.Resource

interface BookingRepository {
    //For service provider
    suspend fun getBookings(serviceProviderId: Long, token: String): List<BookingWithServiceDetails>
    suspend fun updateBookingStatus(info: BookingUpdateInfo, token: String): Boolean

    //For user
    suspend fun bookService(token: String, info: ServiceBookingInfo)
    suspend fun getBookingsByEventId(token: String, eventId: Long): List<BookingWithServiceDetails>
}

