package com.kasra.weather.ui.map

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kasra.weather.data.model.CityInfo
import com.kasra.weather.data.repository.IWeatherRepositoryImpl
import com.kasra.weather.data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * ViewModel for the Map Screen.
 * Handles fetching weather data for multiple cities and managing the UI state.
 */
@HiltViewModel
class MapViewModel
@Inject constructor(
    private val repository: IWeatherRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow() // Expose the state as a StateFlow

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
     * Shows the weather information card for the selected city.
     *
     * @param cityInfo The CityInfo object representing the selected city.
     */
    fun showCardWeather(cityInfo: CityInfo) {
        _state.value = _state.value.copy(isShow = true, cityInfo = cityInfo)
    }

    /**
     * Hides the weather information card.
     */
    fun hideCardWeather() {
        _state.value = _state.value.copy(isShow = false)
    }

    /**
     * Loads weather data for multiple cities and updates the state accordingly.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadListCityInfo() = viewModelScope.launch {
        _state.value = MapState(isLoading = true) // Set loading state
        repository.getWeatherData().collectLatest { resource ->
            when (resource) {
                is Resource.Success -> {
                    // Update state with list of CityInfo objects
                    _state.value =
                        _state.value.copy(listCityInfo = resource.data, isLoading = false)
                }

                is Resource.Error -> {
                    // Update state with error message
                    _state.value = _state.value.copy(error = resource.message, isLoading = false)
                }
            }
        }
    }
}