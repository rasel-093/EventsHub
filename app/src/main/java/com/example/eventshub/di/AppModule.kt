package com.example.eventshub.di

import android.content.Context
import android.content.SharedPreferences
import com.example.eventshub.data.remote.api.AuthApi
import com.example.eventshub.data.remote.api.BookingApi
import com.example.eventshub.data.remote.api.EventApi
import com.example.eventshub.data.remote.api.MessageApi
import com.example.eventshub.data.remote.api.ServiceApi
import com.example.eventshub.data.remote.api.UserApi
import com.example.eventshub.data.remote.repository.AuthRepositoryImpl
import com.example.eventshub.data.remote.repository.BookingRepositoryImpl
import com.example.eventshub.data.remote.repository.EventRepositoryImpl
import com.example.eventshub.data.remote.repository.MessageRepositoryImpl
import com.example.eventshub.data.remote.repository.ServiceRepositoryImpl
import com.example.eventshub.data.remote.repository.UserRepositoryImpl
import com.example.eventshub.domain.repository.AuthRepository
import com.example.eventshub.domain.repository.BookingRepository
import com.example.eventshub.domain.repository.EventRepository
import com.example.eventshub.domain.repository.MessageRepository
import com.example.eventshub.domain.repository.ServiceRepository
import com.example.eventshub.domain.repository.UserRepository
import com.example.eventshub.presentation.auth.signin.SignInViewModel
import com.example.eventshub.presentation.auth.signup.SignUpViewModel
import com.example.eventshub.presentation.booking.BookingViewModel
import com.example.eventshub.presentation.events.EventsViewModel
import com.example.eventshub.presentation.events.eventdetails.EventDetailViewModel
import com.example.eventshub.presentation.home.HomeViewModel
import com.example.eventshub.presentation.home.detail.ServiceDetailViewModel
import com.example.eventshub.presentation.messages.ChatViewModel
import com.example.eventshub.presentation.messages.MessageListViewModel
import com.example.eventshub.presentation.profile.EditProfileViewModel
import com.example.eventshub.presentation.profile.ProfileViewModel
import com.example.eventshub.presentation.serviceprovider.ServiceProviderViewModel
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
    single { get<Retrofit>().create(MessageApi::class.java) }
    single { get<Retrofit>().create(BookingApi::class.java) }


    // Provide Repositories
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<ServiceRepository> { ServiceRepositoryImpl(get()) }
    single<EventRepository> { EventRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<SharedPreferences> {
        androidApplication().getSharedPreferences("auth", Context.MODE_PRIVATE)
    }
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<BookingRepository> { BookingRepositoryImpl(get()) }



    //Provide ViewModels
    //Auth
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get(), androidApplication().getSharedPreferences("auth", Context.MODE_PRIVATE)) }

    //Home
    viewModel { HomeViewModel(
        get(),
        get()
    ) }
    //Events
    viewModel { EventsViewModel(get(), get()) }
    //Profile
    viewModel { ProfileViewModel(get(), androidApplication().getSharedPreferences("auth", Context.MODE_PRIVATE)) }
    //EditProfile
    viewModel { EditProfileViewModel(get(), get()) }

    // Service for Service provider
    viewModel { ServiceProviderViewModel(
        get(),
        preferences = get()
    ) }

    //Provides ServiceDetailsViewmodel
    viewModel { ServiceDetailViewModel(get(), get()) }
    //Provide event details viewmodel
    viewModel { EventDetailViewModel(get(), get()) }

    //Message and Chatviewmodel
    viewModel{ MessageListViewModel(get(), get())}
    viewModel{ ChatViewModel(get(), get()) }

    //Booking viewmodel
    viewModel { BookingViewModel(get(), get()) }
}
