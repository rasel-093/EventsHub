package com.example.eventshub.domain.repository

import com.example.eventshub.data.model.Service
import com.example.eventshub.data.model.ServiceRatingInfo
import com.example.eventshub.domain.model.ServiceEventInfo
import com.example.eventshub.util.Resource

interface ServiceRepository {
    //For user
    suspend fun fetchServices(token: String): Resource<List<Service>>
    //Add service to event
    suspend fun addServiceToEvent(info: ServiceEventInfo, token: String): Resource<String>
    suspend fun rateService(token: String, ratingInfo: ServiceRatingInfo): Result<String>
    suspend fun getServiceById(serviceId: Long, token: String): Result<Service>



    //For service provider
    suspend fun getServicesByServiceProvider(userId: Long, token: String): Resource<List<Service>>
    suspend fun deleteService(serviceId: Long, token: String): Resource<String>
    suspend fun createService(service: Service, token: String): Resource<String>
    suspend fun updateService(service: Service, token: String): Resource<String>

}
