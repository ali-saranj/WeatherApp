package com.kasra.weather.ui.map

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.kasra.weather.data.location.LocationTracker
import com.kasra.weather.data.model.CityInfo
import com.kasra.weather.data.repository.IWeatherRepositoryImpl
import com.kasra.weather.data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
) : ViewModel() {

    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow()// Expose the state as a StateFlow

    /**
     * Handles different intents to trigger actions in the ViewModel.
     *
     * @param intent The intent representing the action to be performed.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun handleIntent(intent: MapIntent) {
        when (intent) {
            is MapIntent.LoadListCityInfo -> loadListCityInfo()
        }
    }


    /**
     * Shows the weather information card.*
     * @param marker The marker that was clicked.
     */
    fun showCardWeather(cityInfo: CityInfo) {
        _state.value = _state.value.copy(isShow = true, cityInfo = cityInfo)
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
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadListCityInfo() = viewModelScope.launch {
        _state.value = MapState(isLoading = true)// Set loading state
        repository.getWeatherData().collectLatest {
            when (it) {
                is Resource.Success -> { // Update state with weather data
                    _state.value =
                        _state.value.copy(listCityInfo = it.data, isLoading = false)
                }

                is Resource.Error -> {// Update state with error message
                    _state.value = _state.value.copy(error = it.message, isLoading = false)
                }
            }
        }

    }
}