package com.dreyesyho.myapplication.di

import com.dreyesyho.myapplication.data.WeathersAPI
import com.dreyesyho.myapplication.data.WeathersRepository
import com.dreyesyho.myapplication.data.WeathersRepositoryImpl
import com.dreyesyho.myapplication.viewmodel.WeathersViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

val appModules = module {
    single<WeathersRepository> { WeathersRepositoryImpl(get(), get()) }
    single { Dispatchers.IO }
    single { WeathersViewModel(get()) }
    single {
        Retrofit.Builder()
            .addConverterFactory(
                json.asConverterFactory(contentType = "application/json".toMediaType())
            )
            .baseUrl("")
            .build()
    }
    single { get<Retrofit>().create(WeathersAPI::class.java) }
}