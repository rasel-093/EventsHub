package com.example.eventshub.domain.repository

import com.example.eventshub.data.model.ImageUploadResponse
import com.example.eventshub.data.model.Message
import com.example.eventshub.data.model.MessageRequestInfo
import com.example.eventshub.data.model.UserBasicInfo
import com.example.eventshub.util.Resource
import java.io.InputStream

interface MessageRepository {
    suspend fun sendMessage(token: String, message: Message): Resource<String>
    suspend fun getConnectedUsers(token: String, userId: Long): Resource<List<UserBasicInfo>>
    suspend fun getMessages(token: String, info: MessageRequestInfo): Resource<List<Message>>
    suspend fun uploadImage(token: String, inputStream: InputStream, fileName: String): Resource<ImageUploadResponse>
}