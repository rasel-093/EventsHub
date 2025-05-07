package com.example.eventshub.presentation.home.detail

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.data.model.Service
import com.example.eventshub.data.model.ServiceRatingInfo
import com.example.eventshub.domain.model.ServiceEventInfo
import com.example.eventshub.domain.repository.ServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
class ServiceDetailViewModel(
    private val repository: ServiceRepository,
    private val preferences: SharedPreferences
) : ViewModel() {

    // Add StateFlow to hold the updated Service
    private val _service = MutableStateFlow<Service?>(null)
    val service: StateFlow<Service?> = _service.asStateFlow()

    var isLoading by mutableStateOf(false)
        private set

    var isRatingLoading by mutableStateOf(false)
        private set

    var snackbarMessage by mutableStateOf<String?>(null)
        private set

    var rating by mutableStateOf(0)
        private set

    var feedback by mutableStateOf("")
        private set

    var ratingSuccess by mutableStateOf(false)
        private set

    fun onRatingChange(value: Int) {
        rating = if (rating == value) 0 else value // Toggle behavior
    }

    fun onFeedbackChange(value: String) {
        feedback = value
    }

    fun clearSnackbar() {
        snackbarMessage = null
    }
    fun addServiceToEvent(serviceId: Long, eventId: Long) {
        val token = preferences.getString("token", "") ?: ""
        if (token.isBlank()) {
            snackbarMessage = "Unauthorized"
            return
        }

        viewModelScope.launch {
            isLoading = true
            val result = repository.addServiceToEvent(ServiceEventInfo(eventId, serviceId), token)
            isLoading = false
        }
    }
    // Function to fetch updated service
    private fun fetchService(serviceId: Long) {
        val token = preferences.getString("token", "") ?: ""
        Log.d("Fetch service", token)
        if (token.isBlank()) {
            snackbarMessage = "Unauthorized"
            return
        }

        viewModelScope.launch {
            val result = repository.getServiceById(serviceId, token) // Assume this function exists in repository
            result
                .onSuccess { updatedService ->
                    _service.value = updatedService
                }
                .onFailure {
                    snackbarMessage = "Failed to fetch updated service: ${it.message}"
                }
        }
    }
fun submitRating(serviceId: Long) {
    val token = preferences.getString("token", "") ?: ""
    val userId = preferences.getLong("userId", 0L)
    if (token.isBlank() || userId == 0L) {
        snackbarMessage = "Unauthorized"
        return
    }

    viewModelScope.launch {
        isRatingLoading = true
        val result = repository.rateService(
            token,
            ServiceRatingInfo(serviceId, userId, rating, feedback)
        )
        isRatingLoading = false

        result
            .onSuccess {
                snackbarMessage = "Thank you for your feedback!"
                rating = 0
                feedback = ""
                ratingSuccess = true
                // Fetch updated service after successful rating
                fetchService(serviceId)
            }
            .onFailure {
                snackbarMessage = "Failed: ${it.message}"
                ratingSuccess = false
            }
    }
}
}
