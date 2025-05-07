package com.example.eventshub.presentation.booking
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.data.model.BookingStatus
import com.example.eventshub.data.model.BookingUpdateInfo
import com.example.eventshub.data.model.BookingWithServiceDetails
import com.example.eventshub.data.model.ServiceBookingInfo
import com.example.eventshub.domain.repository.BookingRepository
import kotlinx.coroutines.launch

class BookingViewModel(
    private val repository: BookingRepository,
    private val preferences: SharedPreferences
) : ViewModel() {

    var allBookings by mutableStateOf<List<BookingWithServiceDetails>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    private val userId: Long = preferences.getLong("userId", -1)
    private val token: String = preferences.getString("token", "") ?: ""

    init {
        if (userId != -1L && token.isNotEmpty()) {
            viewModelScope.launch {
                loadBookings()
            }
        }
    }

    suspend fun loadBookings() {
        isLoading = true
        try {
            allBookings = repository.getBookings(userId, token)
        } finally {
            isLoading = false
        }
    }

    suspend fun updateBooking(id: Long, status: BookingStatus) {
        val success = repository.updateBookingStatus(
            BookingUpdateInfo(id, userId, status),
            token
        )
        if (success) {
            allBookings = allBookings.map {
                if (it.bookingId == id) it.copy(bookingStatus = status) else it
            }
        }
    }


    //For user
    fun confirmServiceBooking(info: ServiceBookingInfo) {
        viewModelScope.launch {
            try {
                repository.bookService(token, info)
                // handle success
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    val registeredServices = mutableStateOf<List<BookingWithServiceDetails>>(emptyList())

    fun loadRegisteredServices(eventId: Long) {
        viewModelScope.launch {
            try {
                val bookings = repository.getBookingsByEventId(token, eventId)
                registeredServices.value = bookings
                // update state with bookings
            } catch (e: Exception) {
                // handle error
            }
        }
    }
}
