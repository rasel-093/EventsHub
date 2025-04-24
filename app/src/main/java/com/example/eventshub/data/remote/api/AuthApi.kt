package com.example.eventshub.data.remote.api

import com.example.eventshub.data.model.User
import com.example.eventshub.data.model.UserLogInResult
import com.example.eventshub.data.model.UserSignInInfo
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/signup")
    suspend fun registerUser(@Body user: User)
    @POST("/signin")
    suspend fun signIn(@Body request: UserSignInInfo): UserLogInResult
}
