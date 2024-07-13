package com.kasra.weather.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.kasra.weather.data.datasource.IWeatherDatasource
import com.kasra.weather.data.datasource.IWeatherDatasourceImpl
import com.kasra.weather.data.mappers.toCityInfo
import com.kasra.weather.data.model.CityInfo
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
 * @return A [Flow] emitting [Resource] objects representing the state of the weather data retrieval.
 */

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherData(): Flow<Resource<List<CityInfo>>> =
        flow<Resource<List<CityInfo>>> {
            // Fetch weather data from the data source
            val result = datasource.getWeatherData()
            // Emit Resource objects based on the result
            when (result) {
                is Resource.Success -> emit(Resource.Success(result.data!!.map { it.toCityInfo() }))
                is Resource.Error -> emit(Resource.Error(result.message?:"Unknown error"))
            }

        }.flowOn(Dispatchers.IO)// Perform network operations on IO dispatcher
}