package com.example.eventshub.di

import android.content.Context
import android.content.SharedPreferences
import com.example.eventshub.data.remote.api.AuthApi
import com.example.eventshub.data.remote.api.EventApi
import com.example.eventshub.data.remote.api.ServiceApi
import com.example.eventshub.data.remote.api.UserApi
import com.example.eventshub.data.remote.repository.AuthRepositoryImpl
import com.example.eventshub.data.remote.repository.EventRepositoryImpl
import com.example.eventshub.data.remote.repository.ServiceRepositoryImpl
import com.example.eventshub.data.remote.repository.UserRepositoryImpl
import com.example.eventshub.domain.repository.AuthRepository
import com.example.eventshub.domain.repository.EventRepository
import com.example.eventshub.domain.repository.ServiceRepository
import com.example.eventshub.domain.repository.UserRepository
import com.example.eventshub.presentation.auth.signin.SignInViewModel
import com.example.eventshub.presentation.auth.signup.SignUpViewModel
import com.example.eventshub.presentation.events.EventsViewModel
import com.example.eventshub.presentation.home.HomeViewModel
import com.example.eventshub.presentation.home.shared.SharedServiceViewModel
import com.example.eventshub.presentation.profile.EditProfileViewModel
import com.example.eventshub.presentation.profile.ProfileViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    // Provide Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // Replace with actual base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    //Provide api
    single { get<Retrofit>().create(EventApi::class.java) }
    single { get<Retrofit>().create(AuthApi::class.java) }
    single { get<Retrofit>().create(ServiceApi::class.java) }
    single { get<Retrofit>().create(UserApi::class.java) }

    // Provide Repositories
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<ServiceRepository> { ServiceRepositoryImpl(get()) }
    single<EventRepository> { EventRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<SharedPreferences> {
        androidApplication().getSharedPreferences("auth", Context.MODE_PRIVATE)
    }


    //Provide ViewModels
    //Auth
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get(), androidApplication().getSharedPreferences("auth", Context.MODE_PRIVATE)) }

    //Home
    viewModel { HomeViewModel(
        get(),
        get()
    ) }
    viewModel { SharedServiceViewModel() }

    //Events
    viewModel { EventsViewModel(get(), get()) }
    //Profile
    viewModel { ProfileViewModel(get(), androidApplication().getSharedPreferences("auth", Context.MODE_PRIVATE)) }
    //EditProfile
    viewModel { EditProfileViewModel(get(), get()) }
}
