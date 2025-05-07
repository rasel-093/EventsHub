package com.example.eventshub.data.remote.repository

import com.example.eventshub.data.model.Service
import com.example.eventshub.data.model.ServiceRatingInfo
import com.example.eventshub.data.remote.api.ServiceApi
import com.example.eventshub.domain.model.ServiceEventInfo
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
//For user
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
    // Add service to user event
    override suspend fun addServiceToEvent(info: ServiceEventInfo, token: String): Resource<String> {
        return try {
            val response = api.addServiceToEvent("Bearer $token", info)
            Resource.Success("Service added to event successfully")
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    if (e.code() == 409) {
                        Resource.Error("Service already assigned to this event")
                    } else {
                        Resource.Error("Server error: ${e.code()}")
                    }
                }
                is UnknownHostException -> Resource.Error("No internet connection.")
                is IOException -> Resource.Error("Network error.")
                else -> Resource.Error("Unexpected error: ${e.localizedMessage}")
            }
        }
    }
    //Rate service
    override suspend fun rateService(token: String, ratingInfo: ServiceRatingInfo): Result<String> {
        return try {
            val response = api.rateService(ratingInfo, "Bearer $token")
            if (response.isSuccessful) {
                val message = response.body()?.string() ?: "Rating submitted successfully"
                Result.success(message)
            } else {
                Result.failure(Exception("Failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun getServiceById(serviceId: Long, token: String): Result<Service> {
        return try {
            val response = api.getServiceById("Bearer $token", serviceId)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response"))
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    //For service provider
    override suspend fun getServicesByServiceProvider(userId: Long, token: String): Resource<List<Service>> {
        return try {
            val result = api.getServicesByServiceProvider("Bearer $token", userId)
            Resource.Success(result)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun deleteService(serviceId: Long, token: String): Resource<String> {
        return try {
            api.deleteService("Bearer $token", serviceId)
            Resource.Success("Service deleted successfully")
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun createService(service: Service, token: String): Resource<String> {
        return try {
            api.createService("Bearer $token", service)
            Resource.Success("Service created successfully")
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> Resource.Error("No internet connection.")
                is IOException -> Resource.Error("Network error. Please try again.")
                is HttpException -> Resource.Error("Server error: ${e.code()}")
                else -> Resource.Error("Unexpected error: ${e.localizedMessage}")
            }
        }
    }
    override suspend fun updateService(service: Service, token: String): Resource<String> {
        return try {
            api.updateService("Bearer $token", service)
            Resource.Success("Service updated successfully")
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> Resource.Error("No internet connection.")
                is IOException -> Resource.Error("Network error. Please try again.")
                is HttpException -> Resource.Error("Server error: ${e.code()}")
                else -> Resource.Error("Unexpected error: ${e.localizedMessage}")
            }
        }
    }

    //Extra function
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