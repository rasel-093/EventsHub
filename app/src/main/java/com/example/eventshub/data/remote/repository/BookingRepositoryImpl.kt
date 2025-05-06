package com.example.eventshub.data.remote.repository

import com.example.eventshub.data.model.BookingUpdateInfo
import com.example.eventshub.data.model.BookingWithServiceDetails
import com.example.eventshub.data.model.ServiceBookingInfo
import com.example.eventshub.data.remote.api.BookingApi
import com.example.eventshub.domain.repository.BookingRepository

class BookingRepositoryImpl(
    private val api: BookingApi
) : BookingRepository {

    //For service provider
    override suspend fun getBookings(serviceProviderId: Long, token: String): List<BookingWithServiceDetails> {
        return api.getBookingsByServiceProviderId(serviceProviderId, "Bearer $token")
    }

    override suspend fun updateBookingStatus(info: BookingUpdateInfo, token: String): Boolean {
        return try {
            val response = api.updateBooking(info, "Bearer $token")
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

//For user
override suspend fun bookService(token: String, info: ServiceBookingInfo) {
    val bearerToken = "Bearer $token"
    val response = api.bookService(bearerToken, info)
    if (!response.isSuccessful) {
        throw Exception("Failed to book service: ${response.code()}")
    }
}

    override suspend fun getBookingsByEventId(token: String, eventId: Long): List<BookingWithServiceDetails> {
        val bearerToken = "Bearer $token"
        return api.getBookingsByEventId(bearerToken, eventId)
    }
}

