package com.example.eventshub.data.remote.repository

import com.example.eventshub.data.model.Service
import com.example.eventshub.data.remote.api.ServiceApi
import com.example.eventshub.domain.repository.ServiceRepository
import com.example.eventshub.util.ErrorMessages
import com.example.eventshub.util.Resource
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException


class ServiceRepositoryImpl(
    private val api: ServiceApi
) : ServiceRepository {

    override suspend fun fetchServices(token: String): Resource<List<Service>> {
        return try {
            val services = api.getServices("Bearer $token")
            Resource.Success(services)
        } catch (e: Exception) {
            when (e) {
                is IOException -> Resource.Error(ErrorMessages.NETWORK)
                is HttpException -> Resource.Error("Server issue. (${e.code()})")
                else -> Resource.Error(ErrorMessages.UNKNOWN)
            }
        }
    }

    private fun <T> handleException(e: Exception): Resource<T> {
        return when (e) {
            is CancellationException -> throw e
            is UnknownHostException -> Resource.Error(ErrorMessages.NO_INTERNET)
            is IOException -> Resource.Error(ErrorMessages.NETWORK)
            is HttpException -> Resource.Error("${ErrorMessages.SERVER} (${e.code()})")
            else -> Resource.Error(ErrorMessages.UNKNOWN)
        }
    }
}