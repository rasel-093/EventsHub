package com.example.eventshub.data.remote.repository

import com.example.eventshub.data.model.User
import com.example.eventshub.data.model.UserLogInResult
import com.example.eventshub.data.model.UserSignInInfo
import com.example.eventshub.data.remote.api.AuthApi
import com.example.eventshub.domain.repository.AuthRepository
import com.example.eventshub.util.Resource

class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {

    override suspend fun registerUser(user: User): Resource<Unit> {
        return try {
            api.registerUser(user)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unknown error occurred")
        }
    }

    override suspend fun signIn(request: UserSignInInfo): Resource<UserLogInResult> {
        return try {
            val response = api.signIn(request)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}
