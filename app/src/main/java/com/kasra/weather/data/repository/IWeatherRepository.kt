package com.kasra.weather.data.repository

import com.kasra.weather.data.model.WeatherInfo
import com.kasra.weather.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {
    suspend fun getWeatherData(lat: String, lon: String): Flow<Resource<WeatherInfo>>

}