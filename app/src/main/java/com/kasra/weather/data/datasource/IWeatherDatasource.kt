package com.kasra.weather.data.datasource

import com.kasra.weather.data.local.model.CityInfoEntity
import com.kasra.weather.data.model.CityInfo
import com.kasra.weather.data.network.model.CityDto
import com.kasra.weather.data.util.Resource

interface IWeatherDatasource {
   suspend fun getWeatherData(): Resource<List<CityInfo>>
   suspend fun insertCitiesInDb(cityDtoList: List<CityDto>)
   suspend fun updateCities(cityDtoList: List<CityDto>)
   suspend fun getCities(): List<CityInfoEntity>
}