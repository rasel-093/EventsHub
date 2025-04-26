package com.example.eventshub.presentation.events

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.domain.repository.EventRepository
import com.example.eventshub.util.Resource
import kotlinx.coroutines.launch
import java.time.LocalDate

class EventsViewModel(private val repository: EventRepository) : ViewModel() {

    private val _state = mutableStateOf(EventsState())
    val state: State<EventsState> = _state

    init {
        loadEvents(1L) // Example userId
    }

    private fun loadEvents(userId: Long) {
        viewModelScope.launch {
            _state.value = EventsState(isLoading = true)
            val result = repository.getEventsOfUser(userId)

            _state.value = when (result) {
                is Resource.Success -> {
                    val now = LocalDate.now()
                    val (upcoming, past) = result.data!!.partition {
                        LocalDate.parse(it.date).isAfter(now)
                    }
                    EventsState(upcomingEvents = upcoming, pastEvents = past)
                }
                is Resource.Error -> EventsState(error = result.message)
                else -> EventsState()
            }
        }
    }
}
