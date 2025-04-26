package com.example.eventshub.domain.repository

import com.example.eventshub.data.model.Event
import com.example.eventshub.util.Resource

interface EventRepository {
    suspend fun getEventsOfUser(userId: Long): Resource<List<Event>>
}
