package com.kasra.weather.ui.map

import com.kasra.weather.data.model.CityInfo

/**
 * Represents the state of the Map Screen.
 *
 * @property isLoading Indicates whether weather data is currently being loaded.
 *@property listCityInfo The weather information for the current location, if available.
 * @property error An error message if an error occurred while fetching weather data.
 * @property isShow Indicates whether the weather information card should be displayed.
 */
data class MapState(
    val isLoading: Boolean = false,
    val listCityInfo: List<CityInfo>? = null,
    val error: String? = null,
    val cityInfo: CityInfo? = null,
    val isShow: Boolean = false
)