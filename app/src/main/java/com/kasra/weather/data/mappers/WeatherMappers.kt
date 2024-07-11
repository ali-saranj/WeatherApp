package com.kasra.weather.data.mappers

import com.kasra.weather.data.model.WeatherInfo
import com.kasra.weather.data.network.model.WeatherDto

/**
 * Extension function to convert a [WeatherDto] object (likely received from a weather API)
 * into a [WeatherInfo]object for use within the application.
 */
fun WeatherDto.toWeather(): WeatherInfo {
    return WeatherInfo(
        latitude = coord.lat,
        longitude = coord.lon,
        temperature = main.temp,
        maxTemperature = main.tempMax,
        minTemperature = main.tempMin,
        windSpeed = wind.speed
    )
}