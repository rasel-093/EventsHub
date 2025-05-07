package com.example.eventshub.presentation.events.eventdetails

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.data.model.Event
import com.example.eventshub.data.model.Service
import com.example.eventshub.domain.model.ServiceEventInfo
import com.example.eventshub.domain.repository.EventRepository
import com.example.eventshub.util.Resource
import kotlinx.coroutines.launch

class EventDetailViewModel(
    private val repository: EventRepository,
    private val preferences: SharedPreferences
) : ViewModel() {

    var isLoading by mutableStateOf(false)
    var snackbarMessage by mutableStateOf<String?>(null)
    var services = mutableStateOf<List<Service>>(emptyList())

    fun updateEvent(event: Event) {
        val token = preferences.getString("token", "") ?: ""
        if (token.isBlank()) return

        viewModelScope.launch {
            isLoading = true
            val result = repository.updateEvent(event, token)
            snackbarMessage = result.message
            isLoading = false
        }
    }

    fun loadRegisteredServices(eventId: Long) {
        val token = preferences.getString("token", "") ?: ""
        viewModelScope.launch {
            val result = repository.getServicesOfEvent(eventId, token)
            if (result is Resource.Success) {
                services.value = result.data ?: emptyList()
            }
        }
    }
    fun deleteEvent(eventId: Long, onSuccess: () -> Unit) {
        val token = preferences.getString("token", "") ?: ""
        if (token.isBlank()) return

        viewModelScope.launch {
            isLoading = true
            val result = repository.deleteEvent(eventId, token)
            if (result is Resource.Success) {
                snackbarMessage = result.message
                onSuccess() // Notify UI to navigate back
            } else {
                snackbarMessage = result.message
            }
            isLoading = false
        }
    }

    fun removeServiceFromEvent(eventId: Long, serviceId: Long) {
        val token = preferences.getString("token", "") ?: ""
        if (token.isBlank()) return

        viewModelScope.launch {
            isLoading = true
            val result = repository.removeServiceFromEvent(
                ServiceEventInfo(eventId = eventId, serviceId = serviceId),
                token
            )
            snackbarMessage = result.message
            if (result is Resource.Success) {
                loadRegisteredServices(eventId)
            }
            isLoading = false
        }
    }

    var eventCost by mutableStateOf<Float?>(null)

    fun loadEventCost(eventId: Long) {
        val token = preferences.getString("token", "") ?: ""
        if (token.isBlank()) return

        viewModelScope.launch {
            isLoading = true
            val result = repository.getEventCost(eventId, token)
            if (result is Resource.Success) {
                eventCost = result.data
            } else {
                snackbarMessage = result.message
            }
            isLoading = false
        }
    }



    fun clearMessage() {
        snackbarMessage = null
    }
}
