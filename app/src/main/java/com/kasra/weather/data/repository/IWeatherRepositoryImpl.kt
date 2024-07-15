package com.kasra.weather.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
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
 * Implementation of the IWeatherRepository interface, responsible for providing weather data.
 * It interacts with the IWeatherDatasource to fetch data and emits it as a Flow of Resource objects.
 * @property datasource The IWeatherDatasource instance used to fetch weather data.
 */
class IWeatherRepositoryImpl @Inject constructor(private val datasource: IWeatherDatasourceImpl) :
    IWeatherRepository {

    /**
     * Fetches weather data from the datasource and emits it as a Flow of Resource objects.
     * @return A Flow of Resource objects containing either a list of CityInfo objects on success or an error message on failure.
     */
    override suspend fun getWeatherData(): Flow<Resource<List<CityInfo>>> =
        flow<Resource<List<CityInfo>>> {
            when (val result = datasource.getWeatherData()) {
                is Resource.Success -> {
                    // Emit the list of CityInfo objects as Success
                    emit(Resource.Success(result.data!!))
                }

                is Resource.Error -> {
                    // Emit the error message as Error
                    emit(Resource.Error(result.message ?: "Unknown error"))
                }
            }
        }.flowOn(Dispatchers.IO) // Perform the data fetching on the IO dispatcher
}