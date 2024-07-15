package com.kasra.weather.data.mappers

import com.kasra.weather.data.local.model.CityInfoEntity
import com.kasra.weather.data.model.CityInfo
import com.kasra.weather.data.network.model.CityDto
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Converts a CityDto object to a CityInfo object.
 * @receiver The CityDto object to convert.
 *@return The resulting CityInfo object.
 */
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
        today = DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm")
            .format(LocalDateTime.now()),
        iconWeather = "https://openweathermap.org/img/wn/${weather.first().icon}@2x.png",
        humidity = main.humidity
    )
}

/**
 * Converts a CityDto object to a CityInfoEntity object.
 *@receiver The CityDto object to convert.
 * @return The resulting CityInfoEntity object.
 */
fun CityDto.toCityEntity(): CityInfoEntity {
    return CityInfoEntity(
        id = id,
        city = name,
        latitude = coord.lat,
        longitude = coord.lon,
        temperature = main.temp,
        maxTemperature = main.tempMax,
        minTemperature = main.tempMin,
        windSpeed = wind.speed,
        description = weather.first().description,
        today = DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm")
            .format(LocalDateTime.now()),
        iconWeather = "https://openweathermap.org/img/wn/${weather.first().icon}@2x.png",
        humidity = main.humidity
    )
}

/**
 * Converts a CityInfoEntity object to a CityInfo object.
 * @receiver The CityInfoEntity object to convert.
 * @return The resulting CityInfo object.
 */
fun CityInfoEntity.toCityInfo(): CityInfo {
    return CityInfo(
        city = city,
        latitude = latitude,
        longitude = longitude,
        temperature = temperature,
        maxTemperature = maxTemperature,
        minTemperature = minTemperature,
        windSpeed = windSpeed,
        description = description,
        today = today,
        iconWeather = iconWeather,
        humidity = humidity
    )
}