package com.example.eventshub.presentation.home

import com.example.eventshub.data.model.Service

data class HomeState(
    val isLoading: Boolean = false,
    val services: List<Service> = emptyList(),
    val error: String? = null
)
