package com.example.eventshub.data.remote.api

import com.example.eventshub.data.model.Service
import retrofit2.http.GET
import retrofit2.http.Header

interface ServiceApi {
    @GET("/services")
    suspend fun getServices(
        @Header("Authorization") token: String
    ): List<Service>
}
