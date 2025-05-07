package com.example.eventshub.domain.repository

import com.example.eventshub.data.model.ImageGenerateMessage
import com.example.eventshub.util.Resource

interface AIRepository {
    suspend fun getAIMessages(userId: Long): Resource<List<ImageGenerateMessage>>
    suspend fun sendAIMessage(message: ImageGenerateMessage): Resource<ImageGenerateMessage>
}