package com.example.eventshub.data.remote.repository

import com.example.eventshub.data.model.Event
import com.example.eventshub.data.remote.api.EventApi
import com.example.eventshub.domain.repository.EventRepository
import com.example.eventshub.util.Resource
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

class EventRepositoryImpl(private val api: EventApi) : EventRepository {
    override suspend fun getEventsOfUser(userId: Long): Resource<List<Event>> {
        return try {
            val response = api.getEventsOfUser(userId)
            Resource.Success(response)
        } catch (e: Exception) {
            when (e) {
                is CancellationException -> throw e // Don't catch coroutine cancellations
                is UnknownHostException -> Resource.Error("No internet connection.")
                is IOException -> Resource.Error("Network error. Please try again.")
                is HttpException -> Resource.Error("Server error: ${e.code()}")
                else -> Resource.Error("Something went wrong: ${e.localizedMessage}")
            }
        }
    }
}
