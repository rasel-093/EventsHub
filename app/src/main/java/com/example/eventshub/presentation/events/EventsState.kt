package com.example.eventshub.presentation.events

import com.example.eventshub.data.model.Event

data class EventsState(
    val isLoading: Boolean = false,
    val upcomingEvents: List<Event> = emptyList(),
    val pastEvents: List<Event> = emptyList(),
    val error: String? = null
)