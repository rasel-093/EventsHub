package com.example.eventshub.data.remote.api

import com.example.eventshub.data.model.ImageGenerateMessage
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AIApi {
    @GET("/generateImageMessages/{id}")
    suspend fun getAIMessages(@Path("id") userId: Long): List<ImageGenerateMessage>

    @POST("/generateImage")
    suspend fun sendAIMessage(@Body message: ImageGenerateMessage): ImageGenerateMessage
}