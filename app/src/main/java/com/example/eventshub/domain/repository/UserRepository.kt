package com.example.eventshub.domain.repository

import com.example.eventshub.data.model.ChangeUserInfo
import com.example.eventshub.data.model.User
import com.example.eventshub.util.Resource

interface UserRepository {
    suspend fun getUserById(id: Long, token: String): Resource<User>
    suspend fun changeUserInfo(info: ChangeUserInfo, token: String): Resource<String>
}
