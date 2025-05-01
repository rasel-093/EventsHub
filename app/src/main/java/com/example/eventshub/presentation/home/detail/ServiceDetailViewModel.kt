package com.example.eventshub.presentation.home.detail

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.domain.model.ServiceEventInfo
import com.example.eventshub.domain.repository.ServiceRepository
import kotlinx.coroutines.launch

class ServiceDetailViewModel(
    private val repository: ServiceRepository,
    private val preferences: SharedPreferences
) : ViewModel() {

    var isLoading by mutableStateOf(false)
    var snackbarMessage by mutableStateOf<String?>(null)
        private set

    fun addServiceToEvent(serviceId: Long, eventId: Long) {
        val token = preferences.getString("token", "") ?: ""
        if (token.isBlank()) {
            snackbarMessage = "Unauthorized"
            return
        }

        viewModelScope.launch {
            isLoading = true
            val result = repository.addServiceToEvent(ServiceEventInfo(eventId, serviceId), token)
            snackbarMessage = result.message
            isLoading = false
        }
    }

    fun clearSnackbar() {
        snackbarMessage = null
    }
}
