package com.example.eventshub.presentation.events

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.data.model.Event
import com.example.eventshub.domain.repository.EventRepository
import com.example.eventshub.util.Resource
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

//Working with button
class EventsViewModel(
    private val repository: EventRepository,
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _state = mutableStateOf(EventsState())
    val state: State<EventsState> = _state
    var snackbarMessage by mutableStateOf<String?>(null)
        private set
    init {
        loadEvents()
    }

     fun loadEvents() {
        val userId = preferences.getLong("userId", -1)
        val token = preferences.getString("token", "") ?: ""

        if (userId == -1L || token.isBlank()) {
            _state.value = EventsState(error = "Authorization failed. Please login again.")
            return
        }
         viewModelScope.launch {
             _state.value = _state.value.copy(isLoading = true)

             val result = repository.getEventsOfUser(userId, token)

             if (result is Resource.Success) {
                 val now = LocalDateTime.now()
                 val (upcoming, past) = result.data!!.partition {
                     val eventDateTime = Instant.ofEpochMilli(it.date)
                         .atZone(ZoneId.systemDefault())
                         .toLocalDateTime()
                     !eventDateTime.isBefore(now)
                 }
                 _state.value = _state.value.copy(
                     isLoading = false,
                     upcomingEvents = upcoming.toList(),
                     pastEvents = past.toList(),
                     error = null
                 )
             } else {
                 _state.value = _state.value.copy(
                     isLoading = false,
                     error = result.message ?: "Unknown error"
                 )
             }
         }

     }

fun createEvent(event: Event) {
    val token = preferences.getString("token", "") ?: ""
    val userId = preferences.getLong("userId", -1)
    val eventWithUserId = event.copy(organizerId = userId)

    if (token.isBlank()) {
        snackbarMessage = "Authorization failed."
        return
    }

    viewModelScope.launch {
        _state.value = state.value.copy(isLoading = true)

        val result = repository.createEvent(eventWithUserId, token)

        if (result is Resource.Success) {
            snackbarMessage = "Event created successfully!"
            loadEvents() // ✅ use a fresh load
        } else {
            snackbarMessage = result.message ?: "Failed to create event."
            _state.value = state.value.copy(isLoading = false)
        }
    }
}
    fun clearSnackbarMessage() {
        snackbarMessage = null
    }
}
