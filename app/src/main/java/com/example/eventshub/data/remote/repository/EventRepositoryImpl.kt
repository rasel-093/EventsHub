package com.example.eventshub.data.remote.repository

import android.util.Log
import com.example.eventshub.data.model.Event
import com.example.eventshub.data.model.Service
import com.example.eventshub.data.remote.api.EventApi
import com.example.eventshub.domain.model.ServiceEventInfo
import com.example.eventshub.domain.repository.EventRepository
import com.example.eventshub.util.ErrorMessages
import com.example.eventshub.util.Resource
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

class EventRepositoryImpl(private val api: EventApi) : EventRepository {
    override suspend fun getEventsOfUser(userId: Long, token: String): Resource<List<Event>> {
        return try {
            val response = api.getEventsOfUser("Bearer $token", userId)
//            Log.d("Fetched event list response", response.toString())
            Resource.Success(response)
        } catch (e: Exception) {
//            Log.e("Fetched event list", e.localizedMessage ?: "Unknown error", e)
            when (e) {
                is CancellationException -> throw e
                is UnknownHostException -> Resource.Error("No internet connection.")
                is IOException -> Resource.Error("Network error. Please try again.")
                is HttpException -> Resource.Error("Server error: ${e.code()}")
                else -> Resource.Error("Something went wrong: ${e.localizedMessage}")
            }
        }
    }
    override suspend fun createEvent(event: Event, token: String): Resource<Event> {
        return try {
            val result = api.createEvent("Bearer $token", event)
            Resource.Success(result)
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> Resource.Error(ErrorMessages.NO_INTERNET)
                is IOException -> Resource.Error(ErrorMessages.NETWORK)
                is HttpException -> Resource.Error("Server issue (${e.code()})")
                else -> Resource.Error(ErrorMessages.UNKNOWN)
            }
        }
    }
    override suspend fun updateEvent(event: Event, token: String): Resource<String> {
        return try {
            api.updateEvent("Bearer $token", event)
            Resource.Success("Event updated successfully!")
        } catch (e: Exception) {
            when (e) {
                is HttpException -> Resource.Error("Server error: ${e.code()}")
                is IOException -> Resource.Error("Network error")
                else -> Resource.Error("Unexpected error: ${e.localizedMessage}")
            }
        }
    }
    override suspend fun deleteEvent(eventId: Long, token: String): Resource<String> {
        return try {
            api.deleteEvent("Bearer $token", eventId)
            Resource.Success("Event deleted successfully.")
        } catch (e: Exception) {
            when (e) {
                is HttpException -> Resource.Error("Server error: ${e.code()}")
                is IOException -> Resource.Error("Network error. Please try again.")
                else -> Resource.Error("Unexpected error: ${e.localizedMessage}")
            }
        }
    }

    override suspend fun getServicesOfEvent(eventId: Long, token: String): Resource<List<Service>> {
        return try {
            val result = api.getServicesOfEvent("Bearer $token", eventId)
            Resource.Success(result)
        } catch (e: Exception) {
            Log.d("getServicesOfEvent", e.localizedMessage ?: "Unknown error")
            Resource.Error("Failed to fetch registered services")
        }
    }

    override suspend fun removeServiceFromEvent(info: ServiceEventInfo, token: String): Resource<String> {
        return try {
            api.removeServiceFromEvent("Bearer $token", info)
            Resource.Success("Service removed successfully.")
        } catch (e: Exception) {
            when (e) {
                is HttpException -> Resource.Error("Server error: ${e.code()}")
                is IOException -> Resource.Error("Network error. Please try again.")
                else -> Resource.Error("Unexpected error: ${e.localizedMessage}")
            }
        }
    }



}
