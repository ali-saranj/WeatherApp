package com.kasra.weather.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.kasra.weather.data.model.WeatherInfo
import com.kasra.weather.data.network.model.WeatherDto
import java.time.format.DateTimeFormatter

/**
 * Extension function to convert a [WeatherDto] object (likely received from a weather API)
 * into a [WeatherInfo]object for use within the application.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDto.toWeather(): WeatherInfo {
    return WeatherInfo(
        latitude = coord.lat,
        longitude = coord.lon,
        temperature = main.temp,
        maxTemperature = main.tempMax,
        minTemperature = main.tempMin,
        windSpeed = wind.speed,
        description = weather.first().description,
        today = DateTimeFormatter.ofPattern("yyyy-MM-dd\nHH:mm").format(java.time.LocalDateTime.now()),
        iconWeather = "https://openweathermap.org/img/wn/${weather.first().icon}@2x.png"
    )
}