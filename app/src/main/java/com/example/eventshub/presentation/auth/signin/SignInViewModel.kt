package com.example.eventshub.presentation.auth.signin

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.data.model.UserSignInInfo
import com.example.eventshub.domain.repository.AuthRepository
import com.example.eventshub.util.Resource
import kotlinx.coroutines.launch
import androidx.core.content.edit

class SignInViewModel(
    private val authRepository: AuthRepository,
    private val sharedPref: SharedPreferences
) : ViewModel() {

    private val _state = mutableStateOf(SignInState())
    val state: State<SignInState> = _state

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.Submit -> {
                if (!validateFields(event.email, event.password)) return

                viewModelScope.launch {
                    _state.value = SignInState(isLoading = true)

                    val result = authRepository.signIn(UserSignInInfo(event.email, event.password))

                    _state.value = when (result) {
                        is Resource.Success -> {
                            if (event.remember) {
                                sharedPref.edit() {
                                    putLong("userId", result.data!!.userId)
                                        .putString("token", result.data.token)
                                }
                            }
                            SignInState(isSuccess = true)
                        }

                        is Resource.Error -> SignInState(error = result.message)
                        else -> SignInState()
                    }
                }
            }
        }
    }

    private fun validateFields(email: String, password: String): Boolean {
        return when {
            email.isBlank() -> {
                _state.value = SignInState(error = "Email cannot be empty")
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _state.value = SignInState(error = "Invalid email format")
                false
            }
            password.isBlank() -> {
                _state.value = SignInState(error = "Password cannot be empty")
                false
            }
            else -> true
        }
    }
}
