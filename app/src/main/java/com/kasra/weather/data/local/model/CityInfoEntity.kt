package com.kasra.weather.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_info")
data class CityInfoEntity(
    @PrimaryKey val id: Int,
    val city: String,
    val latitude: Double,
    val longitude: Double,
    val temperature: Double,
    val maxTemperature: Double,
    val minTemperature: Double,
    val windSpeed: Double,
    val description: String,
    val iconWeather: String,
    val today: String,
    val humidity: Int
)
