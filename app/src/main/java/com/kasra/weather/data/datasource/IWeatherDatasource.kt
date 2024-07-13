package com.kasra.weather.data.datasource

import com.kasra.weather.data.network.model.CityDto
import com.kasra.weather.data.util.Resource

interface IWeatherDatasource {
   suspend fun getWeatherData(): Resource<List<CityDto>>
}