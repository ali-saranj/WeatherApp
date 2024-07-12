package com.kasra.weather.data.datasource

import com.kasra.weather.data.network.IWeatherApi
import com.kasra.weather.data.network.model.WeatherDto
import com.kasra.weather.data.util.Resource
import javax.inject.Inject


/**
 * Implementation of [IWeatherDatasource] that fetches weather data from the network using [IWeatherApi].
 */
class IWeatherDatasourceImpl @Inject constructor(val api: IWeatherApi) : IWeatherDatasource {

    /**
     * Retrieves weather data for the given latitude and longitude.
     *
     * @param lat Latitude of the location.
     * @param lon Longitude of the location.
     * @return A [Result] object wrapping either the [WeatherDto] on success or a [Throwable] on failure.
     */
    override suspend fun getWeatherData(lat: String, lon: String): Resource<WeatherDto> {
        try {
            val response = api.getWeatherData(lat, lon)
            return if (response.isSuccessful) {
                Resource.Success(response.body()!!)// Consider handling potential null response body
            } else {
                return Resource.Error(
                    response.errorBody().toString()
                ) // Consider more specific error handling
            }
        }catch (e: Exception){
            e.printStackTrace()
            return Resource.Error(
                e.message.toString()
            ) // Consider more specific error handling
        }

    }
}