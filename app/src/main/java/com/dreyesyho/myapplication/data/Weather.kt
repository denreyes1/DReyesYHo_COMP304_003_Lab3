package com.dreyesyho.myapplication.data

import android.content.Context
import com.dreyesyho.myapplication.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.roundToInt

@Serializable
data class WeatherResponse(
    @SerialName("coord") val coord: Coord,
    @SerialName("weather") val weather: List<Weather>,
    @SerialName("base") val base: String,
    @SerialName("main") val main: Main,
    @SerialName("visibility") val visibility: Int,
    @SerialName("wind") val wind: Wind,
    @SerialName("clouds") val clouds: Clouds,
    @SerialName("dt") val dt: Long,
    @SerialName("sys") val sys: Sys,
    @SerialName("timezone") val timezone: Int,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("cod") val cod: Int,
    val isFavorite: Boolean = false
)

@Serializable
data class Coord(
    @SerialName("lon") val lon: Double,
    @SerialName("lat") val lat: Double
)

@Serializable
data class Weather(
    @SerialName("id") val id: Int,
    @SerialName("main") val main: String,
    @SerialName("description") val description: String,
    @SerialName("icon") val icon: String
)

@Serializable
data class Main(
    @SerialName("temp") val temp: Double,
    @SerialName("feels_like") val feelsLike: Double,
    @SerialName("temp_min") val tempMin: Double,
    @SerialName("temp_max") val tempMax: Double,
    @SerialName("pressure") val pressure: Int,
    @SerialName("humidity") val humidity: Int,
    @SerialName("sea_level") val seaLevel: Int,
    @SerialName("grnd_level") val grndLevel: Int
)

@Serializable
data class Wind(
    @SerialName("speed") val speed: Double,
    @SerialName("deg") val deg: Int
)

@Serializable
data class Clouds(
    @SerialName("all") val all: Int
)

@Serializable
data class Sys(
    @SerialName("type") val type: Int? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("country") val country: String,
    @SerialName("sunrise") val sunrise: Long,
    @SerialName("sunset") val sunset: Long
)

//// Return Mock data
fun getMockWeatherData(): List<WeatherResponse> {
    val toronto = WeatherResponse(
        coord = Coord(lon = -79.4163, lat = 43.7001),
        weather = listOf(Weather(id = 804, main = "Clouds", description = "overcast clouds", icon = "04n")),
        base = "stations",
        main = Main(temp = 285.27, feelsLike = 285.02, tempMin = 284.44, tempMax = 286.21, pressure = 1004, humidity = 95, seaLevel = 1004, grndLevel = 988),
        visibility = 10000,
        wind = Wind(speed = 5.14, deg = 220),
        clouds = Clouds(all = 100),
        dt = 1731291463,
        sys = Sys(type = 1, id = 718, country = "CA", sunrise = 1731240362, sunset = 1731275842),
        timezone = -18000,
        id = 6167865,
        name = "Toronto",
        cod = 200
    )

    val calgary = WeatherResponse(
        coord = Coord(lon = -114.0719, lat = 51.0447),
        weather = listOf(Weather(id = 800, main = "Clear", description = "clear sky", icon = "01d")),
        base = "stations",
        main = Main(temp = 280.32, feelsLike = 278.85, tempMin = 279.15, tempMax = 281.48, pressure = 1020, humidity = 55, seaLevel = 1020, grndLevel = 1013),
        visibility = 16093,
        wind = Wind(speed = 2.57, deg = 300),
        clouds = Clouds(all = 0),
        dt = 1731291463,
        sys = Sys(type = 1, id = 718, country = "CA", sunrise = 1731242362, sunset = 1731277862),
        timezone = -25200,
        id = 5913490,
        name = "Calgary",
        cod = 200
    )

    val vancouver = WeatherResponse(
        coord = Coord(lon = -123.1216, lat = 49.2827),
        weather = listOf(Weather(id = 500, main = "Rain", description = "light rain", icon = "10d")),
        base = "stations",
        main = Main(temp = 288.65, feelsLike = 287.59, tempMin = 287.15, tempMax = 289.82, pressure = 1018, humidity = 85, seaLevel = 1018, grndLevel = 1011),
        visibility = 9000,
        wind = Wind(speed = 3.09, deg = 180),
        clouds = Clouds(all = 75),
        dt = 1731291463,
        sys = Sys(type = 1, id = 718, country = "CA", sunrise = 1731243362, sunset = 1731278862),
        timezone = -28800,
        id = 6173331,
        name = "Vancouver",
        cod = 200
    )

    return listOf(toronto, calgary, vancouver)
}

fun kelvinToCelsius(kelvin: Double): Int {
    val celsius = kelvin - 273.15
    return celsius.roundToInt()
}

fun getWeatherIcon(weatherCondition: String, isDaytime: Boolean): Int {
    return when (weatherCondition) {
        "Clear" -> if(isDaytime) R.drawable.clear_day else R.drawable.clear_night
        "Clouds" -> if(isDaytime) R.drawable.partly_cloudy_day else R.drawable.partly_cloudy_night
        "Rain" -> R.drawable.showers_rain
        "Snow" -> R.drawable.showers_snow
        "Thunderstorm" -> R.drawable.strong_thunderstorms
        "Mist", "Fog" -> R.drawable.haze_fog_dust_smoke
        else -> R.drawable.cloudy
    }
}

fun isDaytime(response: WeatherResponse): Boolean {
    return isDaytime(response.dt, response.sys.sunrise, response.sys.sunset)
}

fun isDaytime(dt: Long, sunriseTime: Long, sunsetTime: Long): Boolean {
    return dt in sunriseTime until sunsetTime
}

fun getWindDirection(degrees: Int): String {
    return when {
        degrees in 337..360 || degrees in 0..22 -> "N"
        degrees in 23..67 -> "NE"
        degrees in 68..112 -> "E"
        degrees in 113..157 -> "SE"
        degrees in 158..202 -> "S"
        degrees in 203..247 -> "SW"
        degrees in 248..292 -> "W"
        degrees in 293..336 -> "NW"
        else -> "N/A"
    }
}

fun formatTime(timestamp: Long, timezoneOffset: Int): String {
    val date = Date(timestamp * 1000)
    val format = SimpleDateFormat("hh:mm a", Locale.getDefault())

    // Apply the timezone offset from the API
    format.timeZone = TimeZone.getTimeZone("GMT").apply {
        rawOffset = timezoneOffset * 1000
    }

    return format.format(date)
}