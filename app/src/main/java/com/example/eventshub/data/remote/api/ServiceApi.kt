package com.example.eventshub.data.remote.api

import com.example.eventshub.data.model.Service
import retrofit2.http.GET

interface ServiceApi {
    @GET("/services")
    suspend fun getServices(): List<Service>
}
