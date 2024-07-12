package com.kasra.weather.data.repository

import com.kasra.weather.data.datasource.IWeatherDatasource
import com.kasra.weather.data.datasource.IWeatherDatasourceImpl
import com.kasra.weather.data.mappers.toWeather
import com.kasra.weather.data.model.WeatherInfo
import com.kasra.weather.data.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Implementation of [IWeatherRepository] that fetches weather data using [IWeatherDatasource]
 * and exposes it as a [Flow] of [Resource] objects.
 */

class IWeatherRepositoryImpl @Inject constructor(private val datasource: IWeatherDatasourceImpl) : IWeatherRepository {
/**
 * Retrieves weather data for the given latitude and longitude as a [Flow] of [Resource].
 ** @param lat Latitude of the location.
 * @param lon Longitude of the location.
 * @return A [Flow] emitting [Resource] objects representing the state of the weather data retrieval.
 */

    override suspend fun getWeatherData(lat: String, lon: String): Flow<Resource<WeatherInfo>> =
        flow<Resource<WeatherInfo>> {
            // Fetch weather data from the data source
            val result = datasource.getWeatherData(lat, lon)
            // Emit Resource objects based on the result
            when (result) {
                is Resource.Success -> emit(Resource.Success(result.data!!.toWeather()))
                is Resource.Error -> emit(Resource.Error(result.message?:"Unknown error"))
            }

        }.flowOn(Dispatchers.IO)// Perform network operations on IO dispatcher
}