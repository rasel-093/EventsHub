package com.example.eventshub.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.domain.repository.ServiceRepository
import com.example.eventshub.util.Resource
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ServiceRepository) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        fetchServices()
    }

    private fun fetchServices() {
        viewModelScope.launch {
            _state.value = HomeState(isLoading = true)
            val result = repository.fetchServices()
            _state.value = when (result) {
                is Resource.Success -> HomeState(services = result.data ?: emptyList())
                is Resource.Error -> HomeState(error = result.message)
                else -> HomeState()
            }
        }
    }
}
