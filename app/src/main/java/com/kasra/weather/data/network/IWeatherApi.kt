package com.kasra.weather.data.network

import com.kasra.weather.data.network.model.WeatherDto
import com.kasra.weather.data.util.Content
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherApi {
    @GET("weather")
    fun getWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = Content.API_KEY,
        @Query("units") units: String = "metric"
    ): Response<WeatherDto>

}