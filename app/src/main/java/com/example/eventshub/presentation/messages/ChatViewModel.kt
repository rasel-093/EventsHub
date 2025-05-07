package com.example.eventshub.presentation.messages
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.data.model.ImageUploadResponse
import com.example.eventshub.data.model.Message
import com.example.eventshub.data.model.MessageRequestInfo
import com.example.eventshub.domain.repository.MessageRepository
import com.example.eventshub.util.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class ChatViewModel(
    private val repository: MessageRepository,
    private val preferences: SharedPreferences
) : ViewModel() {
    var messages = mutableStateOf<List<Message>>(emptyList())
    var isLoading = mutableStateOf(false)
    var error = mutableStateOf<String?>(null)
    var success = mutableStateOf<String?>(null)

    fun loadMessages(receiverId: Long) {
        val token = preferences.getString("token", "") ?: ""
        val senderId = preferences.getLong("userId", -1)
        if (token.isBlank() || senderId == -1L) {
            error.value = "Authentication error."
            return
        }

        viewModelScope.launch {
            isLoading.value = true

            try {
                val result = repository.getMessages(token, MessageRequestInfo(receiverId, senderId))
                messages.value = result.data ?: emptyList()
                //Log.e("ChatViewModel", "Error loading messages ${result.data}")
            } catch (e: Exception) {
                //Log.e("ChatViewModel", "Error loading messages ${e.localizedMessage}")
                error.value = e.localizedMessage ?: "Unexpected error"
            } finally {
                isLoading.value = false
            }
        }
    }
    fun uploadImage(uri: Uri, context: Context, onResult: (Resource<ImageUploadResponse>?) -> Unit) {
        val token = preferences.getString("token", "") ?: run {
            error.value = "Authentication error."
            onResult(null)
            return
        }

        viewModelScope.launch {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                if (inputStream != null) {
                    val result = repository.uploadImage(token, inputStream, "image.jpg")
                    inputStream.close()
                    onResult(result)
                } else {
                    error.value = "Failed to read image"
                    onResult(null)
                }
            } catch (e: Exception) {
                error.value = e.localizedMessage ?: "Unexpected error"
                onResult(null)
            }
        }
    }

    fun sendMessage(receiverId: Long, text: String, imageUri: Uri?, context: Context) {
        val token = preferences.getString("token", "") ?: ""
        val senderId = preferences.getLong("userId", -1)
        if (token.isBlank() || senderId == -1L) {
            error.value = "Authentication error."
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            try {
                var imageLink: String? = null
                if (imageUri != null) {
                    // Use suspendCancellableCoroutine to await uploadImage result
                    val uploadResult = suspendCancellableCoroutine<Resource<ImageUploadResponse>?> { continuation ->
                        uploadImage(imageUri, context) { result ->
                            continuation.resume(result)
                        }
                    }
                    if (uploadResult is Resource.Success) {
                        imageLink = uploadResult.data?.imageLink
                    } else {
                        error.value = uploadResult?.message ?: "Failed to upload image"
                        isLoading.value = false
                        return@launch
                    }
                }

                val message = Message(
                    senderId = senderId,
                    receiverId = receiverId,
                    text = text,
                    imageLink = imageLink,
                    sentAt = System.currentTimeMillis()
                )
                val sendResult = repository.sendMessage(token, message)
                if (sendResult is Resource.Success) {
                    success.value = sendResult.data
                    loadMessages(receiverId)
                } else {
                    error.value = sendResult.message
                }
            } catch (e: Exception) {
                error.value = e.localizedMessage ?: "Unexpected error"
            } finally {
                isLoading.value = false
            }
        }
    }
}
