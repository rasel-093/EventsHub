package com.example.eventshub.presentation.image

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventshub.data.model.ImageUploadResponse
import com.example.eventshub.domain.repository.ImageRepository
import kotlinx.coroutines.launch
import java.io.File

class ImageUploadViewModel(
    private val repo: ImageRepository
) : ViewModel() {

    var imageUrl by mutableStateOf<String?>(null)
        private set

    var isUploading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun uploadImage(file: File, onResult: (ImageUploadResponse?) -> Unit) {
        viewModelScope.launch {
            try {
                isUploading = true
                val response = repo.uploadImage(file)
                onResult(response)
            } catch (e: Exception) {
                errorMessage = e.message
                onResult(null)
            } finally {
                isUploading = false
            }
        }
    }

}
