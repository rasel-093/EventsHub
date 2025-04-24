package com.example.eventshub.presentation.home.shared

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.eventshub.data.model.Service

class SharedServiceViewModel : ViewModel() {
    private val _services = mutableStateListOf<Service>()
    private val services: List<Service> = _services

    fun setServices(serviceList: List<Service>) {
        _services.clear()
        _services.addAll(serviceList)
    }

    fun getServiceById(id: Long): Service? {
        return services.find { it.id == id }
    }
}
