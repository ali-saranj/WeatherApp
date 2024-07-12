package com.kasra.weather.ui.map

import android.util.Log
import android.widget.Toast
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
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.currentCameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.kasra.weather.ui.component.CardWeather

/**
 * Composable function representing the Map Screen.
 * It displays a map with a marker at the user's location and shows weatherinformation when the marker is clicked.
 *
 * @param viewModel The MapViewModel instance used to handle state and events.
 */
@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    //Load weather location when the screen is launched
    LaunchedEffect(viewModel) {
        viewModel.handleIntent(MapIntent.LoadWeatherLocation)
    }

    // Display different content based on the state
    when {
        state.isLoading -> { // Show a loading indicator while fetching data
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {// Display an error message if an error occurred
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "Error: ${state.error}")
            }
        }

        state.weatherInfo != null -> { // Display the map with a marker and weather information card
            MapContent(
                latitude = state.weatherInfo!!.latitude,
                longitude = state.weatherInfo!!.longitude,
                onInfoWindowClick = viewModel::showCardWeather,
                onMapClick = viewModel::hideCardWeather
            )
            // Animate the visibility of the weather card
            AnimatedVisibility(
                visible = state.isShow,
                enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }),
                exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight })
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CardWeather(
                        weatherInfo = state.weatherInfo!!,
                        backgroundColor = Color(0xFF009688),
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
 * Composable function displaying the map content with a marker.
 *
 * @param latitude Latitude of the marker position.
 * @param longitude Longitude of the marker position.
 * @param onInfoWindowClick Callback to be executed when the marker's info window is clicked.
 * @param onMapClick Callback to be executed when the map is clicked.
 */
@Composable
fun MapContent(
    latitude: Double,
    longitude: Double,
    onInfoWindowClick: (Marker) -> Unit,
    onMapClick: (LatLng) -> Unit
) {

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(latitude, longitude), 15f)
        },
        onMapClick = onMapClick
    ) {
        // Place a marker at the specified location
        Marker(
            state = rememberMarkerState(position = LatLng(latitude, longitude)),
            title = "your location",
            snippet = "Click to get weather info",
            onInfoWindowClick = onInfoWindowClick,
        )
    }
}


