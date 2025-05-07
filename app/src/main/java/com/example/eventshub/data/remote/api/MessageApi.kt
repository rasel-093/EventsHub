package com.example.eventshub.data.remote.api

import com.example.eventshub.data.model.ImageUploadResponse
import com.example.eventshub.data.model.Message
import com.example.eventshub.data.model.MessageRequestInfo
import com.example.eventshub.data.model.UserBasicInfo
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface MessageApi {
    @POST("/sendMessage")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Body message: Message
    ): ResponseBody

    @GET("/getConnectedPeople/{id}")
    suspend fun getConnectedUsers(
        @Header("Authorization") token: String,
        @Path("id") userId: Long
    ): List<UserBasicInfo>

    @POST("/messages")
    suspend fun getMessages(
        @Header("Authorization") token: String,
        @Body info: MessageRequestInfo
    ): List<Message>

    @Multipart
    @POST("upload")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): ImageUploadResponse
}