package com.kasra.weather.ui.map

import com.kasra.weather.data.model.WeatherInfo
import kotlinx.coroutines.flow.MutableStateFlow


data class MapState(
    val isLoading: Boolean = false,
    val weatherInfo: WeatherInfo? = null,
    val error: String? = null,
    val isShow: Boolean = false
)