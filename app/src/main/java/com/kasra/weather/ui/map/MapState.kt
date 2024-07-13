package com.kasra.weather.ui.map

import com.kasra.weather.data.model.CityInfo


/**
 * Represents the state of the Map Screen.
 *
 * @property isLoading Indicates whether weather data is currently being loaded.
 *@property listCityInfo The list of CityInfo objects representing weather information for different cities, if available.
 * @property error An error message if an error occurred while fetching weather data.
 * @property cityInfo The CityInfo object representing the selected city for displaying detailed weather information, if available.
 * @property isShow Indicates whether the weather information card should be displayed.
 */
data class MapState(
    val isLoading: Boolean = false,
    val listCityInfo: List<CityInfo>? = null,
    val error: String? = null,
    val cityInfo: CityInfo? = null,
    val isShow: Boolean = false
)