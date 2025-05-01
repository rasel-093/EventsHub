package com.example.eventshub.domain.repository

import com.example.eventshub.data.model.*
import com.example.eventshub.util.Resource

interface MessageRepository {
    suspend fun sendMessage(token: String, message: Message): Resource<String>
    suspend fun getConnectedUsers(token: String, userId: Long): Resource<List<UserBasicInfo>>
    suspend fun getMessages(token: String, info: MessageRequestInfo): Resource<List<Message>>
}