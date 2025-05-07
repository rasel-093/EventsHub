package com.example.eventshub.presentation.auth.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.data.model.User
import com.example.eventshub.domain.repository.AuthRepository
import com.example.eventshub.util.Resource
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = mutableStateOf(SignUpState())
    val state: State<SignUpState> = _state

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.Submit -> {
                if (!validateInputs(
                        name = event.name,
                        email = event.email,
                        phone = event.phone,
                        password = event.password,
                        confirmPassword = event.confirmPassword
                    )
                ) return

                val user = User(
                    name = event.name,
                    email = event.email,
                    phone = event.phone,
                    password = event.password,
                    role = event.role
                )

                register(user)
            }
        }
    }

    private fun validateInputs(
        name: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        return when {
            name.isBlank() -> {
                _state.value = SignUpState(error = "Name cannot be empty")
                false
            }
            email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _state.value = SignUpState(error = "Invalid email format")
                false
            }
            phone.isBlank() -> {
                _state.value = SignUpState(error = "Phone number is required")
                false
            }
            password.length < 6 -> {
                _state.value = SignUpState(error = "Password must be at least 6 characters")
                false
            }
            password != confirmPassword -> {
                _state.value = SignUpState(error = "Passwords do not match")
                false
            }
            else -> true
        }
    }

    private fun register(user: User) {
        viewModelScope.launch {
            _state.value = SignUpState(isLoading = true)

            val result = authRepository.registerUser(user)

            _state.value = when (result) {
                is Resource.Success -> SignUpState(isSuccess = true)
                is Resource.Error ->SignUpState(error = result.message)
                else -> SignUpState()
            }
        }
    }
}

