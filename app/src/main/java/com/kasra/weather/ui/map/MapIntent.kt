package com.kasra.weather.ui.map

sealed class MapIntent {
    data object LoadWeatherLocation : MapIntent()
    data object ClickMarker : MapIntent()
}