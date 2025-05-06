package com.example.eventshub.data.remote.repository

import android.util.Log
import com.example.eventshub.data.model.Message
import com.example.eventshub.data.model.MessageRequestInfo
import com.example.eventshub.data.model.UserBasicInfo
import com.example.eventshub.data.remote.api.MessageApi
import com.example.eventshub.domain.repository.MessageRepository
import com.example.eventshub.util.Resource
import retrofit2.HttpException
import java.io.IOException

class MessageRepositoryImpl(private val api: MessageApi) : MessageRepository {
    override suspend fun sendMessage(token: String, message: Message): Resource<String> {
        return try {
            api.sendMessage("Bearer $token", message)
            Resource.Success("Message sent successfully")
        } catch (e: Exception) {
            Log.e("MessageRepositoryImpl", "Error sending message: ${e.localizedMessage}")
            Log.d("MessageRepositoryImpl", "Error sending message: $token")
            when (e) {
                is HttpException -> Resource.Error("Server error: ${e.code()}")
                is IOException -> Resource.Error("Network error. Please try again.")
                else -> Resource.Error("Unexpected error: ${e.localizedMessage}")
            }
        }
    }

    override suspend fun getConnectedUsers(token: String, userId: Long): Resource<List<UserBasicInfo>> {
        return try {
            val users = api.getConnectedUsers("Bearer $token", userId)
            Resource.Success(users)
        } catch (e: Exception) {
            Log.e("MessageRepositoryImpl", "Error getting connected users: ${e.localizedMessage}")
            when (e) {
                is HttpException -> Resource.Error("Server error: ${e.code()}")
                is IOException -> Resource.Error("Network error. Please try again.")
                else -> Resource.Error("Unexpected error: ${e.localizedMessage}")
            }
        }
    }

    override suspend fun getMessages(token: String, info: MessageRequestInfo): Resource<List<Message>> {
        return try {
            val messages = api.getMessages("Bearer $token", info)
            Log.d("API_DEBUG", "Messages response: $messages")
            Resource.Success(messages)
        } catch (e: Exception) {
            Log.e("ChatViewModel", "Error loading messages ${e.localizedMessage}")
            when (e) {
                is HttpException -> Resource.Error("Server error: ${e.code()}")
                is IOException -> Resource.Error("Network error. Please try again.")
                else -> Resource.Error("Unexpected error: ${e.localizedMessage}")
            }
        }
    }
}