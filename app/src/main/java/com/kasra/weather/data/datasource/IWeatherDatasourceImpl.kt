package com.kasra.weather.data.datasource

import com.kasra.weather.data.local.Dao.CityInfoDao
import com.kasra.weather.data.local.model.CityInfoEntity
import com.kasra.weather.data.mappers.toCityEntity
import com.kasra.weather.data.mappers.toCityInfo
import com.kasra.weather.data.model.CityInfo
import com.kasra.weather.data.network.IWeatherApi
import com.kasra.weather.data.network.model.CityDto
import com.kasra.weather.data.util.Resource
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Implementation of the IWeatherDatasource interface, responsible for fetching weather data
 * from the API and interacting with the local database.*
 * @property api The IWeatherApi instance used to make network requests.
 * @property cityInfoDao The CityInfoDao instance used to access the local database for city information.
 */
class IWeatherDatasourceImpl @Inject constructor(
    private val api: IWeatherApi, private val cityInfoDao: CityInfoDao
) : IWeatherDatasource {

    /**
     * Fetches weather data, handles API response, database interactions, and network errors.
     * @return A Resource object containing either the list of CityInfo objects on success or an error message on failure.
     */
    override suspend fun getWeatherData(): Resource<List<CityInfo>> {
        return try {
            val response = api.getWeatherData()
            if (response.isSuccessful) {
                // Handle successful API response
                try {
                    // Attempt to insert new cities into the database
                    insertCitiesInDb(response.body()!!.list)
                } catch (e: Exception) {
                    // If insertion fails (likely due to existing entries), update existing cities
                    updateCities(response.body()!!.list)
                }
                // Return success with mapped CityInfo objects
                Resource.Success(response.body()!!.list.map { it.toCityInfo() })
            } else {
                // Return error with message from response body
                Resource.Error(response.errorBody().toString())
            }
        } catch (e: java.net.UnknownHostException) {
            // Handle network errors (no internet connection)
            return if (getCities().isNotEmpty()) {
                // Return success with data from the local database if available
                Resource.Success(getCities().map { it.toCityInfo() })
            } else {
                // Return error if no local data is available
                Resource.Error(e.message.toString())
            }
        } catch (e: Exception) {
            // Handle other exceptions
            e.printStackTrace() // Log the exception for debugging
            Resource.Error(e.message.toString()) // Return error with exception message
        }
    }

    /**
     * Inserts a list of CityDto objects into the local database as CityInfoEntity objects.
     * @param cityDtoList The list of CityDto objects to insert.
     */
    override suspend fun insertCitiesInDb(cityDtoList: List<CityDto>) {
        cityDtoList.forEach { cityDto ->
            cityInfoDao.insert(cityDto.toCityEntity())
        }
    }

    /**
     * Updates existing city information in the local database with data from the provided CityDto objects.
     *
     * @param cityDtoList The list of CityDto objects containing updated information.
     */
    override suspend fun updateCities(cityDtoList: List<CityDto>) {
        cityDtoList.forEach { cityDto ->
            cityInfoDao.update(cityDto.toCityEntity())
        }
    }

    /**
     * Retrieves all CityInfoEntity objects from the local database.
     *
     * @return A list of CityInfoEntity objects representing city information stored in the database.
     */
    override suspend fun getCities(): List<CityInfoEntity> {
        return cityInfoDao.getAll()
    }
}