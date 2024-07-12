package com.kasra.weather.ui.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

@HiltViewModel
class MapViewModel @OptIn(ExperimentalCoroutinesApi::class)
@Inject constructor(
    private val repository: IWeatherRepositoryImpl,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow()

    fun handleIntent(intent: MapIntent) {
        when (intent) {
            is MapIntent.LoadWeatherLocation -> loadWeatherLocation()
            is MapIntent.ClickMarker -> clickMarker()
        }
    }

    private fun clickMarker() = viewModelScope.launch {
        _state.value = _state.value.copy(isShow = _state.value.isShow.not())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadWeatherLocation() = viewModelScope.launch {
        _state.value = MapState(isLoading = true)
        locationTracker.getCurrentLocation()?.let { location ->
            repository.getWeatherData(
                lat = location.latitude.toString(),
                lon = location.longitude.toString()
            )
                .collectLatest {
                    when (it) {
                        is Resource.Success -> {
                            _state.value =
                                _state.value.copy(weatherInfo = it.data, isLoading = false)
                        }

                        is Resource.Error -> {
                            _state.value = _state.value.copy(error = it.message, isLoading = false)
                        }
                    }
                }
        }
    }


}