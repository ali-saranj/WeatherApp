package com.kasra.weather.ui.map

/**
 * Represents the different intents (actions) that can be triggered in the Map Screen.
 */
sealed class MapIntent {
    //Intent to load weather data for the current location.
    data object LoadWeatherLocation : MapIntent()
}