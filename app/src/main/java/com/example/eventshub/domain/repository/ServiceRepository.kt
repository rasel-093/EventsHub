package com.example.eventshub.domain.repository

import com.example.eventshub.data.model.Service
import com.example.eventshub.util.Resource

interface ServiceRepository {
    suspend fun fetchServices(): Resource<List<Service>>
}
