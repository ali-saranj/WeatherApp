package com.kasra.weather.data.network

import com.kasra.weather.data.network.model.DataResponse
import com.kasra.weather.data.util.Content
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherApi {
    @GET("group")
    suspend fun getWeatherData(
        @Query("id") id: String = "112931,124665,418863,115019,113646,144448,119208,127566,118743,1113217,121801,132144,111822,141681,134217,140463,116402,443565,116421",
        @Query("appid") appid: String = Content.API_KEY,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "en"
    ): Response<DataResponse>

}

suspend fun main() {
    val retrofit = Retrofit.Builder().baseUrl(Content.BASE_URL).addConverterFactory(
        GsonConverterFactory.create()
    ).build().create(IWeatherApi::class.java)

    println(retrofit.getWeatherData().body().toString())

}