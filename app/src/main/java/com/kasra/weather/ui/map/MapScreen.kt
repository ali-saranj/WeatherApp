package com.kasra.weather.ui.map

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.kasra.weather.data.model.CityInfo
import com.kasra.weather.ui.components.CardWeather

@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // Load list of cities weather info when the screen is launched
    LaunchedEffect(viewModel) {
        viewModel.handleIntent(MapIntent.LoadListCityInfo)
    }

    // Display different content based on the state
    when {
        state.isLoading -> {
            // Show a loading indicator while fetching data
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            // Display an error message if an error occurred
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${state.error}")
            }
        }

        state.listCityInfo != null -> {
            // Display the map with markers for each city and weather information card
            MapContent(
                listCityInfo = state.listCityInfo!!,
                onInfoWindowClick = viewModel::showCardWeather,
                onMapClick = { viewModel.hideCardWeather() }
            )

            // Animate the visibility of the weather card
            AnimatedVisibility(
                visible = state.isShow,
                enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }),
                exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight })
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CardWeather(
                        weatherInfo = state.cityInfo!!,
                        backgroundColor = Color(0XFF88C0D0),
                        onClose = viewModel::hideCardWeather
                    )
                }
            }
        }
    }
}

@Preview(name = "MapScreen")
@Composable
private fun PreviewMapScreen() {
    MapScreen()
}

/**
 * Composable function displaying the map content with markers for each city.
 *
 * @param listCityInfo List of CityInfo objects representing weather information for different cities.
 * @param onInfoWindowClick Callback to be executed when a marker's info window is clicked, providing the corresponding CityInfo.
 * @param onMapClick Callback to be executed when the map is clicked, providing the LatLng of the click.
 */
@Composable
fun MapContent(
    listCityInfo: List<CityInfo>,
    onInfoWindowClick: (CityInfo) -> Unit,
    onMapClick: (LatLng) -> Unit
) {
    // Display the Google Map
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(32.0, 53.0), 5f)
        },
        onMapClick = onMapClick
    ) {
        // Place markers for each city on the map
        listCityInfo.forEach { cityInfo ->
            Marker(
                state = rememberMarkerState(
                    position = LatLng(
                        cityInfo.latitude,
                        cityInfo.longitude
                    )
                ),
                title = cityInfo.city,
                snippet = cityInfo.description,
                onInfoWindowClick = { onInfoWindowClick(cityInfo) }
            )
        }
    }
}




