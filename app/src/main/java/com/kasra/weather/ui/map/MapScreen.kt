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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.currentCameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.handleIntent(MapIntent.LoadWeatherLocation)
    }
    when {
        state.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "Error: ${state.error}")
            }
        }

        state.weatherInfo != null -> {
            MapContent(
                latitude = state.weatherInfo!!.latitude,
                longitude = state.weatherInfo!!.longitude,
                onInfoWindowClick = { viewModel.handleIntent(MapIntent.ClickMarker)},
            )
            AnimatedVisibility(
                visible = state.isShow,
                enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }),
                exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight })
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    TODO()
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

@Composable
fun MapContent(
    latitude: Double,
    longitude: Double,
    onInfoWindowClick: () -> Unit
) {

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(latitude, longitude), 15f)
        }
    ) {
        Marker(
            state = rememberMarkerState(position = LatLng(latitude, longitude)),
            title = "your location",
            snippet = "Click to get weather info",
            onInfoWindowClick = { onInfoWindowClick() }
        )
    }
}

