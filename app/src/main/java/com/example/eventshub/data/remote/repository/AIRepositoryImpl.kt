package com.example.eventshub.data.remote.repository

import android.util.Log
import com.example.eventshub.data.model.ImageGenerateMessage
import com.example.eventshub.data.remote.api.AIApi
import com.example.eventshub.domain.repository.AIRepository
import com.example.eventshub.util.Resource

class AIRepositoryImpl(
    private val api: AIApi
) : AIRepository {
    override suspend fun getAIMessages(userId: Long): Resource<List<ImageGenerateMessage>> {
        return try {
            val response = api.getAIMessages(userId)
            Log.d("AiRepo get", response.toString())
            Resource.Success(response)
        } catch (e: Exception) {
            Log.d("AiRepo get", e.toString())
            Resource.Error(e.localizedMessage ?: "Failed to load messages")
        }
    }

    override suspend fun sendAIMessage(message: ImageGenerateMessage): Resource<ImageGenerateMessage> {
        return try {
            val response = api.sendAIMessage(message)
            Log.d("AiRepo send", response.toString())
            Resource.Success(response)
        } catch (e: Exception) {
            Log.d("AiRepo send", e.localizedMessage)
            Resource.Error(e.localizedMessage ?: "Failed to send message")
        }
    }
}