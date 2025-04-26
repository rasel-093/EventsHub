package com.example.eventshub.data.remote.api

import com.example.eventshub.data.model.ChangeUserInfo
import com.example.eventshub.data.model.User
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @GET("/user/{id}")
    suspend fun getUserById(
        @Path("id") id: Long,
        @Header("Authorization") token: String
    ): User

    @POST("/changeUserInfo")
    suspend fun changeUserInfo(
        @Header("Authorization") token: String,
        @Body request: ChangeUserInfo
    ): ResponseBody

}
