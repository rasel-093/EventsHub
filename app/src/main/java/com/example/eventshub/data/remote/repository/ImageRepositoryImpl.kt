package com.example.eventshub.data.remote.repository

import com.example.eventshub.data.model.ImageUploadResponse
import com.example.eventshub.data.remote.api.ImageApi
import com.example.eventshub.domain.repository.ImageRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ImageRepositoryImpl(
    private val api: ImageApi
) : ImageRepository {

    override suspend fun uploadImage(file: File): ImageUploadResponse {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val response = api.uploadImage(body)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("Upload failed: ${response.code()}")
        }
    }
}
