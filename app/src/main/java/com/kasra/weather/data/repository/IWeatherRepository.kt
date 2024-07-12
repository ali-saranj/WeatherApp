package com.kasra.weather.data.repository

import com.kasra.weather.data.model.WeatherInfo
import com.kasra.weather.data.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Interface for weather data repository.
 * @param lat Latitude of the location.
 * @param lon Longitude of the location.
 * @return A [Flow] emitting [Resource] objects representing the state of the weather data retrieval.
 */
interface IWeatherRepository {
    // Retrieves weather data for the given latitude and longitude as a [Flow] of [Resource].
    suspend fun getWeatherData(lat: String, lon: String): Flow<Resource<WeatherInfo>>

}