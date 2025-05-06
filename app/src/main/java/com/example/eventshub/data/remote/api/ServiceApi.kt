package com.example.eventshub.data.remote.api

import com.example.eventshub.data.model.Service
import com.example.eventshub.domain.model.ServiceEventInfo
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ServiceApi {
    //Returns service for user
    @GET("/services")
    suspend fun getServices(
        @Header("Authorization") token: String
    ): List<Service>
    //User add service to an event
    @POST("/addServicesToEvent")
    suspend fun addServiceToEvent(
        @Header("Authorization") token: String,
        @Body info: ServiceEventInfo
    ): ResponseBody


    //For Service provider
    @GET("/servicesByProvider/{id}")
    suspend fun getServicesByServiceProvider(
        @Header("Authorization") token: String,
        @Path("id") serviceProviderId: Long
    ): List<Service>

    //Delete service
    @DELETE("/service/{id}")
    suspend fun deleteService(
        @Header("Authorization") token: String,
        @Path("id") serviceId: Long
    ): ResponseBody

    //Create service
    @POST("/service")
    suspend fun createService(
        @Header("Authorization") token: String,
        @Body service: Service
    ): ResponseBody
    //Update service
    @POST("/updateService")
    suspend fun updateService(
        @Header("Authorization") token: String,
        //@Path("id") id: Long,
        @Body service: Service
    ): ResponseBody
}
