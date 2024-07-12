package com.kasra.weather.ui.map

import com.kasra.weather.data.model.WeatherInfo
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Represents the state of the Map Screen.
 *
 * @property isLoading Indicates whether weather data is currently being loaded.
 *@property weatherInfo The weather information for the current location, if available.
 * @property error An error message if an error occurred while fetching weather data.
 * @property isShow Indicates whether the weather information card should be displayed.
 */
data class MapState(
    val isLoading: Boolean = false,
    val weatherInfo: WeatherInfo? = null,
    val error: String? = null,
    val isShow: Boolean = false
)