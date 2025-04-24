package com.example.eventshub.domain.repository

import com.example.eventshub.data.model.User
import com.example.eventshub.data.model.UserLogInResult
import com.example.eventshub.data.model.UserSignInInfo
import com.example.eventshub.util.Resource

interface AuthRepository {
    suspend fun registerUser(user: User): Resource<Unit>
    suspend fun signIn(request: UserSignInInfo): Resource<UserLogInResult>
}
