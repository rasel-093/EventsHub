package com.example.eventshub.data.remote.api

import com.example.eventshub.data.model.Event
import retrofit2.http.GET
import retrofit2.http.Path

interface EventApi {
    @GET("/eventOfUser/{id}")
    suspend fun getEventsOfUser(@Path("id") userId: Long): List<Event>
}
