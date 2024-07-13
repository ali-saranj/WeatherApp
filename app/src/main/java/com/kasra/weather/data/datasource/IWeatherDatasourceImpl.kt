package com.kasra.weather.data.datasource

import com.kasra.weather.data.network.IWeatherApi
import com.kasra.weather.data.network.model.CityDto
import com.kasra.weather.data.util.Resource
import javax.inject.Inject


/**
 * Implementation of [IWeatherDatasource] that fetches weather data from the network using [IWeatherApi].
 */
class IWeatherDatasourceImpl @Inject constructor(val api: IWeatherApi) : IWeatherDatasource {

    /**
     * Retrieves weather data for the given latitude and longitude.
     *
     * @return A [Result] object wrapping either the [List of CityDto] on success or a [Throwable] on failure.
     */
    override suspend fun getWeatherData(): Resource<List<CityDto>> {
        try {
            val response = api.getWeatherData()
            return if (response.isSuccessful) {
                Resource.Success(response.body()!!.list)// Consider handling potential null response body
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