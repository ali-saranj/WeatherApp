package com.kasra.weather.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.kasra.weather.data.model.CityInfo
import com.kasra.weather.data.network.model.CityDto
import java.time.format.DateTimeFormatter

/**
 * Extension function to convert a [CityDto] object (likely received from a weather API)
 * into a [CityInfo]object for use within the application.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun CityDto.toCityInfo(): CityInfo {
    return CityInfo(
        city = name,
        latitude = coord.lat,
        longitude = coord.lon,
        temperature = main.temp,
        maxTemperature = main.tempMax,
        minTemperature = main.tempMin,
        windSpeed = wind.speed,
        description = weather.first().description,
        today = DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm").format(java.time.LocalDateTime.now()),
        iconWeather = "https://openweathermap.org/img/wn/${weather.first().icon}@2x.png",
        humidity = main.humidity
    )
}