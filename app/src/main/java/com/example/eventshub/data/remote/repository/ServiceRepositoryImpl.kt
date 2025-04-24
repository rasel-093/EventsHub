package com.example.eventshub.data.remote.repository

import com.example.eventshub.data.model.Service
import com.example.eventshub.data.remote.api.ServiceApi
import com.example.eventshub.domain.repository.ServiceRepository
import com.example.eventshub.util.Resource

class ServiceRepositoryImpl(private val api: ServiceApi) : ServiceRepository {
    override suspend fun fetchServices(): Resource<List<Service>> {
        return try {
            val response = api.getServices()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}
