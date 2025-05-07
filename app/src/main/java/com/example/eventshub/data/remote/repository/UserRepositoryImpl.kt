package com.example.eventshub.data.remote.repository

import com.example.eventshub.data.model.ChangeUserInfo
import com.example.eventshub.data.model.User
import com.example.eventshub.data.remote.api.UserApi
import com.example.eventshub.domain.repository.UserRepository
import com.example.eventshub.util.ErrorMessages
import com.example.eventshub.util.Resource
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class UserRepositoryImpl(private val api: UserApi) : UserRepository {
    override suspend fun getUserById(id: Long, token: String): Resource<User> {
        return try {
            val user = api.getUserById(id, "Bearer $token")
            Resource.Success(user)
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> Resource.Error(ErrorMessages.NO_INTERNET)
                is IOException -> Resource.Error(ErrorMessages.NETWORK)
                is HttpException -> Resource.Error("${ErrorMessages.SERVER} (${e.code()})")
                else -> Resource.Error(ErrorMessages.UNKNOWN)
            }
        }
    }
    override suspend fun changeUserInfo(info: ChangeUserInfo, token: String): Resource<String> {
        return try {
            val response = api.changeUserInfo("Bearer $token", info)
            val message = response.string() // Read raw string safely
            Resource.Success(message)
        } catch (e: Exception) {
//            Log.e("ChangeUserInfo", "Failed: ${e.localizedMessage}", e)
            when (e) {
                is UnknownHostException -> Resource.Error(ErrorMessages.NO_INTERNET)
                is IOException -> Resource.Error(ErrorMessages.NETWORK)
                is HttpException -> Resource.Error("Server issue (${e.code()})")
                else -> Resource.Error(ErrorMessages.UNKNOWN)
            }
        }
    }
}

