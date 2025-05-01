package com.example.eventshub.data.remote.api

import com.example.eventshub.data.model.Event
import com.example.eventshub.data.model.Service
import com.example.eventshub.domain.model.ServiceEventInfo
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface EventApi {
    //For user
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

    @POST("/updateEvent")
    suspend fun updateEvent(
        @Header("Authorization") token: String,
        @Body event: Event
    ): ResponseBody

    @GET("/servicesByEventId/{id}")
    suspend fun getServicesOfEvent(
        @Header("Authorization") token: String,
        @Path("id") eventId: Long
    ): List<Service>

    @DELETE("/event/{id}")
    suspend fun deleteEvent(
        @Header("Authorization") token: String,
        @Path("id") eventId: Long
    ): ResponseBody

    @HTTP(method = "DELETE", path = "/removeServicesFromEvent", hasBody = true)
    suspend fun removeServiceFromEvent(
        @Header("Authorization") token: String,
        @Body info: ServiceEventInfo
    ): ResponseBody




}
