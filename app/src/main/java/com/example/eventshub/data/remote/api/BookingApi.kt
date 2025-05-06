package com.example.eventshub.data.remote.api

import com.example.eventshub.data.model.BookingUpdateInfo
import com.example.eventshub.data.model.BookingWithServiceDetails
import com.example.eventshub.data.model.ServiceBookingInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface BookingApi {
    //For Service provider
    @GET("serviceProviderBooking/{id}")
    suspend fun getBookingsByServiceProviderId(
        @Path("id") serviceProviderId: Long,
        @Header("Authorization") token: String
    ): List<BookingWithServiceDetails>

    @POST("updateBooking")
    suspend fun updateBooking(
        @Body bookingUpdateInfo: BookingUpdateInfo,
        @Header("Authorization") token: String
    ): Response<String>


    //For user
    @POST("/bookService")
    suspend fun bookService(
        @Header("Authorization") token: String,
        @Body info: ServiceBookingInfo
    ): Response<Unit>

    @GET("/eventBooking/{eventId}")
    suspend fun getBookingsByEventId(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: Long
    ): List<BookingWithServiceDetails>
}

