package com.kasra.weather.data.network

import com.kasra.weather.data.network.model.WeatherDto
import com.kasra.weather.data.util.Content
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherApi {
    @GET("weather")
    suspend fun getWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = Content.API_KEY,
        @Query("units") units: String = "metric"
    ): Response<WeatherDto>

}

suspend fun main() {
    val retrofit = Retrofit.Builder().baseUrl(Content.BASE_URL).addConverterFactory(
        GsonConverterFactory.create()
    ).build().create(IWeatherApi::class.java)

    println(retrofit.getWeatherData("35", "139").body().toString())

}