package com.example.eventshub.di

import android.content.Context
import com.example.eventshub.data.remote.api.AuthApi
import com.example.eventshub.data.remote.repository.AuthRepositoryImpl
import com.example.eventshub.domain.repository.AuthRepository
import com.example.eventshub.domain.repository.ServiceRepository
import com.example.eventshub.presentation.auth.signin.SignInViewModel
import com.example.eventshub.presentation.auth.signup.SignUpViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import com.example.eventshub.presentation.home.HomeViewModel
import com.example.eventshub.data.remote.repository.ServiceRepositoryImpl
import com.example.eventshub.presentation.home.shared.SharedServiceViewModel

@OptIn(ExperimentalSerializationApi::class)
val appModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://your-api-base-url.com") // Replace with your actual base URL
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(AuthApi::class.java)
    }

    single<AuthRepository> { AuthRepositoryImpl(get()) }

    //Auth
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get(), androidApplication().getSharedPreferences("auth", Context.MODE_PRIVATE)) }

    //Home
    single<ServiceRepository> { ServiceRepositoryImpl(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { SharedServiceViewModel() }
}
