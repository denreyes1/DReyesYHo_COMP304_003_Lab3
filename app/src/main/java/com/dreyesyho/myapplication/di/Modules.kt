package com.dreyesyho.myapplication.di

import com.dreyesyho.myapplication.data.WeathersAPI
import com.dreyesyho.myapplication.data.WeathersRepository
import com.dreyesyho.myapplication.data.WeathersRepositoryImpl
import com.dreyesyho.myapplication.viewmodel.WeatherViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit

val appModules = module {
    single<WeathersRepository> { WeathersRepositoryImpl(get(), get()) }
    single { Dispatchers.IO }
    single { WeatherViewModel(get()) }
    single {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType)) // Kotlinx serialization converter
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build()
    }
    single { get<Retrofit>().create(WeathersAPI::class.java) }
}