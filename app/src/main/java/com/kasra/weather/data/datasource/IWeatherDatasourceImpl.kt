package com.kasra.weather.data.datasource

import com.kasra.weather.data.network.IWeatherApi
import com.kasra.weather.data.network.model.CityDto
import com.kasra.weather.data.util.Resource
import javax.inject.Inject


/**
 * Implementation of the IWeatherDatasource interface, responsible for fetching weather data from the API.
 *
 * @property api The IWeatherApi instance used to make network requests.
 */
class IWeatherDatasourceImpl @Inject constructor(private val api: IWeatherApi) :
    IWeatherDatasource {

    /**
     * Fetches weather data from the API and returns it wrapped in a Resource object.
     *
     * @returnA Resource object containing either the list of CityDto objects on success or an error message on failure.
     */
    override suspend fun getWeatherData(): Resource<List<CityDto>> {
        return try {
            val response = api.getWeatherData()
            if (response.isSuccessful) {
                Resource.Success(response.body()!!.list) // Success with data
            } else {
                Resource.Error(
                    response.errorBody().toString()
                ) // Error with message from response body
            }
        } catch (e: Exception) {
            e.printStackTrace() // Log the exception for debugging
            Resource.Error(e.message.toString()) // Error with exception message
        }
    }
}