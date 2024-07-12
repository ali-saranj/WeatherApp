package com.kasra.weather.data.model

data class WeatherInfo(
    val latitude: Double,
    val longitude: Double,
    val temperature: Double,
    val maxTemperature: Double,
    val minTemperature: Double,
    val windSpeed: Double,
    val description: String,
    val iconWeather: String,
    val today: String
)
