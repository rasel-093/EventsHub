package com.example.eventshub.domain.repository

import com.example.eventshub.data.model.ImageUploadResponse
import java.io.File

interface ImageRepository {
    suspend fun uploadImage(file: File): ImageUploadResponse
}
