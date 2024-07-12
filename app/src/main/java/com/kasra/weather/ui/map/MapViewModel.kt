package com.kasra.weather.ui.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.kasra.weather.data.location.LocationTracker
import com.kasra.weather.data.model.WeatherInfo
import com.kasra.weather.data.repository.IWeatherRepository
import com.kasra.weather.data.repository.IWeatherRepositoryImpl
import com.kasra.weather.data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Map Screen.
 * Handles fetching weather data based on the user's location and managing the UI state.
 */
@HiltViewModel
class MapViewModel @OptIn(ExperimentalCoroutinesApi::class)
@Inject constructor(
    private val repository: IWeatherRepositoryImpl,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow()// Expose the state as a StateFlow

    /**
     * Handles different intents to trigger actions in the ViewModel.
     *
     * @param intent The intent representing the action to be performed.
     */
    fun handleIntent(intent: MapIntent) {
        when (intent) {
            is MapIntent.LoadWeatherLocation -> loadWeatherLocation()
        }
    }

    /**
     * Shows the weather information card.*
     * @param marker The marker that was clicked.
     */
    fun showCardWeather(marker: Marker) {
        _state.value = _state.value.copy(isShow = true)
    }
    /**
     * Hides the weather information card.
     *
     * @param latLng The LatLng of the map click (optional).
     */
    fun hideCardWeather(latLng: LatLng? = null) {
        _state.value = _state.value.copy(isShow = false)
    }

    /**
     * Loads weather data for the current location.
     * Updates the state with loading, success, or error status.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadWeatherLocation() = viewModelScope.launch {
        _state.value = MapState(isLoading = true)// Set loading state
        locationTracker.getCurrentLocation()?.let { location ->// Fetch weather data for the current location
            repository.getWeatherData(
                lat = location.latitude.toString(),
                lon = location.longitude.toString()
            )
                .collectLatest {
                    when (it) {
                        is Resource.Success -> { // Update state with weather data
                            _state.value =
                                _state.value.copy(weatherInfo = it.data, isLoading = false)
                        }

                        is Resource.Error -> {// Update state with error message
                            _state.value = _state.value.copy(error = it.message, isLoading = false)
                        }
                    }
                }
        }
    }

}