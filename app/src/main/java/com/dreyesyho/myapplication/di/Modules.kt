package com.dreyesyho.myapplication.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.dreyesyho.myapplication.data.WeatherDatabase
import com.dreyesyho.myapplication.data.WeathersAPI
import com.dreyesyho.myapplication.data.WeathersRepository
import com.dreyesyho.myapplication.data.WeathersRepositoryImpl
import com.dreyesyho.myapplication.viewmodel.WeatherViewModel
import com.dreyesyho.myapplication.workers.WeatherSyncWorker
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module
import retrofit2.Retrofit

val appModules = module {
    single<WeathersRepository> { WeathersRepositoryImpl(get(), get(), get()) }
    single { Dispatchers.IO }
    viewModel { WeatherViewModel(get()) }
    single {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType)) // Kotlinx serialization converter
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build()
    }
    single { get<Retrofit>().create(WeathersAPI::class.java) }

    single {
        Room.databaseBuilder(
            androidContext(),
            WeatherDatabase::class.java,
            "weather-database"
        ).setJournalMode(RoomDatabase.JournalMode.TRUNCATE).build()
        //.setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
    }
    single { get<WeatherDatabase>().weatherDao() }

    worker { WeatherSyncWorker(get(), get(), get()) }
}