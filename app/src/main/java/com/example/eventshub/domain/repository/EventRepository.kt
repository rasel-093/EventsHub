package com.example.eventshub.domain.repository

import com.example.eventshub.data.model.Event
import com.example.eventshub.data.model.Service
import com.example.eventshub.domain.model.ServiceEventInfo
import com.example.eventshub.util.Resource

interface EventRepository {
    //For user
    suspend fun createEvent(event: Event, token: String): Resource<Event>
    suspend fun getEventsOfUser(userId: Long, token: String): Resource<List<Event>>
    suspend fun updateEvent(event: Event, token: String): Resource<String>
    suspend fun getServicesOfEvent(eventId: Long, token: String): Resource<List<Service>>
    suspend fun deleteEvent(eventId: Long, token: String): Resource<String>
    suspend fun removeServiceFromEvent(info: ServiceEventInfo, token: String): Resource<String>
    suspend fun getEventCost(eventId: Long, token: String): Resource<Float>


}
