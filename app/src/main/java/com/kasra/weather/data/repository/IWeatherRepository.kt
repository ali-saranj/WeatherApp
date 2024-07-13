package com.kasra.weather.data.repository

import com.kasra.weather.data.model.CityInfo
import com.kasra.weather.data.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Interface for weather data repository.
 * @return A [Flow] emitting [Resource] objects representing the state of the weather data retrieval.
 */
interface IWeatherRepository {
    // Retrieves weather data for the given latitude and longitude as a [Flow] of [Resource].
    suspend fun getWeatherData(): Flow<Resource<List<CityInfo>>>

}