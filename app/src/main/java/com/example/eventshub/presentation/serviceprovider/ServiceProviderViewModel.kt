package com.example.eventshub.presentation.serviceprovider

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.data.model.Service
import com.example.eventshub.domain.repository.ServiceRepository
import com.example.eventshub.util.Resource
import kotlinx.coroutines.launch

class ServiceProviderViewModel(
    private val repository: ServiceRepository,
    private val preferences: SharedPreferences
) : ViewModel() {

    var services = mutableStateOf<List<Service>>(emptyList())
        private set

    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)
    var snackbarMessage = mutableStateOf<String?>(null)

    private val userId = preferences.getLong("userId", -1)
    private val token = preferences.getString("token", "") ?: ""

    init {
        loadMyServices()
    }

    fun loadMyServices() {
        if (userId == -1L || token.isBlank()) {
            errorMessage.value = "Authorization failed"
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getServicesByServiceProvider(userId, token)
            if (result is Resource.Success) {
                services.value = result.data ?: emptyList()
            } else {
                errorMessage.value = result.message
            }
            isLoading.value = false
        }
    }

    fun createService(service: Service) {
        val token = preferences.getString("token", "") ?: ""
        val userId = preferences.getLong("userId", -1)
        val serviceWithUser = service.copy(serviceProviderId = userId)

        if (token.isBlank()) {
            snackbarMessage.value = "Unauthorized"
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            val result = repository.createService(serviceWithUser, token)
            snackbarMessage.value = result.message
            if (result is Resource.Success) {
                loadMyServices()
            }
            isLoading.value = false
        }
    }

fun deleteService(serviceId: Long) {
    viewModelScope.launch {
        isLoading.value = true
        val result = repository.deleteService(serviceId, token)
        snackbarMessage.value = result.message
        if (result is Resource.Success) loadMyServices()
        isLoading.value = false
    }
}

    fun updateService(service: Service) {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.updateService(service, token)
            snackbarMessage.value = result.message
            if (result is Resource.Success) {
                loadMyServices()
            }
            isLoading.value = false
        }
    }


    fun clearSnackbarMessage() {
        snackbarMessage.value = null
    }
}
