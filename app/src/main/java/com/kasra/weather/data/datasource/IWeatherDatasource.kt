package com.kasra.weather.data.datasource

import com.kasra.weather.data.network.model.WeatherDto
import com.kasra.weather.data.util.Resource

interface IWeatherDatasource {
   suspend fun getWeatherData(lat: String, lon: String): Resource<WeatherDto>
}