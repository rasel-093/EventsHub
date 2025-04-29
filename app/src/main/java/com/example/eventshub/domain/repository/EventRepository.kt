package com.example.eventshub.domain.repository

import com.example.eventshub.data.model.Event
import com.example.eventshub.util.Resource

interface EventRepository {
    //For user
    suspend fun createEvent(event: Event, token: String): Resource<Event>
    suspend fun getEventsOfUser(userId: Long, token: String): Resource<List<Event>>
}
