package com.example.eventshub.data.remote.repository

import com.example.eventshub.data.model.User
import com.example.eventshub.data.model.UserLogInResult
import com.example.eventshub.data.model.UserSignInInfo
import com.example.eventshub.data.remote.api.AuthApi
import com.example.eventshub.domain.repository.AuthRepository
import com.example.eventshub.util.ErrorMessages
import com.example.eventshub.util.Resource
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {

    override suspend fun registerUser(user: User): Resource<Unit> {
        return try {
            api.registerUser(user)
            Resource.Success(Unit)
        } catch (e: Exception) {
            //Log.e("AUTH_REPO", "IOException: ${e.localizedMessage}", e)
            handleException(e)
        }
    }

    override suspend fun signIn(request: UserSignInInfo): Resource<UserLogInResult> {
        return try {
            val result = api.signIn(request)
            Resource.Success(result)
        } catch (e: Exception) {
            //Log.e("AUTH_REPO", "IOException: ${e.localizedMessage}", e)
            handleException(e)
        }
    }

    private fun <T> handleException(e: Exception): Resource<T> {
        return when (e) {
            is CancellationException -> throw e
            is UnknownHostException -> Resource.Error(ErrorMessages.NO_INTERNET)
            is IOException -> {
//                Log.e("AUTH_REPO", "IOException: ${e.localizedMessage}", e)
                Resource.Error(ErrorMessages.NETWORK)
            }
            is HttpException -> Resource.Error("${ErrorMessages.SERVER} (${e.code()})")
            else -> Resource.Error(ErrorMessages.UNKNOWN)
        }
    }
}
