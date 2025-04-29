package com.example.eventshub.data.remote.api

import com.example.eventshub.data.model.Event
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface EventApi {
    @GET("/eventOfUser/{id}")
    suspend fun getEventsOfUser(
        @Header("Authorization") token: String,
        @Path("id") userId: Long
    ): List<Event>



    @POST("/event")
    suspend fun createEvent(
        @Header("Authorization") token: String,
        @Body event: Event
    ): Event
}
