package com.dreyesyho.myapplication.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

const val APIKEY = "ea378a6dfe032f00ce2f79987d602c7c"

class WeathersRepositoryImpl(
    private val weathersAPI: WeathersAPI,
    private val dispatcher: CoroutineDispatcher,
    private val weatherDao: WeatherDao
): WeathersRepository {

    override suspend fun getWeathers(): Flow<List<WeatherResponse>> {
        return withContext(dispatcher) {
            weatherDao.getWeathers()
                .map { weathersCached ->
                    weathersCached.map { weatherEntity ->
                        WeatherResponse(
                            id = weatherEntity.id,
                            coord = weatherEntity.coord,
                            weather = weatherEntity.weather,
                            base = weatherEntity.base,
                            main = weatherEntity.main,
                            visibility = weatherEntity.visibility,
                            wind = weatherEntity.wind,
                            clouds = weatherEntity.clouds,
                            dt = weatherEntity.dt,
                            sys = weatherEntity.sys,
                            timezone = weatherEntity.timezone,
                            name = weatherEntity.name,
                            cod = weatherEntity.cod,
                            isFavorite = weatherEntity.isFavorite
                        )
                    }
                }
                .onEach {
                    if (it.isEmpty()) {
                        fetchRemoteWeather("toronto")
                        fetchRemoteWeather("calgary")
                        fetchRemoteWeather("vancouver")
                        fetchRemoteWeather("montreal")
                        fetchRemoteWeather("quebec")
                        fetchRemoteWeather("cebu")
                        fetchRemoteWeather("hong kong")
                        fetchRemoteWeather("mexico city")
                    }
                }
        }
    }

    override suspend fun fetchRemoteWeather(location: String) {
        withContext(dispatcher) {
            val response = weathersAPI.fetchWeather(location, APIKEY)
            if (response.isSuccessful) {
                val weather = response.body()
                weatherDao.insert(WeatherEntity(
                    id = weather!!.id,
                    coord = weather.coord,
                    weather = weather.weather,
                    base = weather.base,
                    main = weather.main,
                    visibility = weather.visibility,
                    wind = weather.wind,
                    clouds = weather.clouds,
                    dt = weather.dt,
                    sys = weather.sys,
                    timezone = weather.timezone,
                    name = weather.name,
                    cod = weather.cod,
                    isFavorite = weather.isFavorite
                ))
            }
        }
    }

    override suspend fun updateWeather(weather: WeatherResponse) {
        withContext(dispatcher) {
            weatherDao.update(
                WeatherEntity(
                    id = weather.id,
                    coord = weather.coord,
                    weather = weather.weather,
                    base = weather.base,
                    main = weather.main,
                    visibility = weather.visibility,
                    wind = weather.wind,
                    clouds = weather.clouds,
                    dt = weather.dt,
                    sys = weather.sys,
                    timezone = weather.timezone,
                    name = weather.name,
                    cod = weather.cod,
                    isFavorite = weather.isFavorite
                )
            )
        }
    }

    override suspend fun getFavoriteWeather(): Flow<List<WeatherResponse>> {
        return withContext(dispatcher) {
            weatherDao.getFavoriteWeathers()
                .map { weathersCached ->
                    weathersCached.map { weatherEntity ->
                        WeatherResponse(
                            id = weatherEntity.id,
                            coord = weatherEntity.coord,
                            weather = weatherEntity.weather,
                            base = weatherEntity.base,
                            main = weatherEntity.main,
                            visibility = weatherEntity.visibility,
                            wind = weatherEntity.wind,
                            clouds = weatherEntity.clouds,
                            dt = weatherEntity.dt,
                            sys = weatherEntity.sys,
                            timezone = weatherEntity.timezone,
                            name = weatherEntity.name,
                            cod = weatherEntity.cod,
                            isFavorite = weatherEntity.isFavorite
                        )
                    }
                }
        }
    }

}