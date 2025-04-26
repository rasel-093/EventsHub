package com.example.eventshub.presentation.home

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.domain.repository.ServiceRepository
import com.example.eventshub.util.Resource
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ServiceRepository,
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        fetchServices()
    }

    private fun fetchServices() {
        val token = preferences.getString("token", "") ?: ""
        if (token.isBlank()) {
            _state.value = HomeState(error = "No token found")
            return
        }

        viewModelScope.launch {
            _state.value = HomeState(isLoading = true)
            val result = repository.fetchServices(token)

            _state.value = when (result) {
                is Resource.Success -> HomeState(services = result.data ?: emptyList())
                is Resource.Error -> HomeState(error = result.message)
                is Resource.Loading -> HomeState(isLoading = true)
            }
        }
    }
}

