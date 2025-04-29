package com.example.eventshub.data.remote.repository

import android.util.Log
import com.example.eventshub.data.model.Event
import com.example.eventshub.data.remote.api.EventApi
import com.example.eventshub.domain.repository.EventRepository
import com.example.eventshub.util.ErrorMessages
import com.example.eventshub.util.Resource
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException
import kotlin.math.log

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
}
