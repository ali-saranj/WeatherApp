package com.kasra.weather.ui.map

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.kasra.weather.data.model.CityInfo
import com.kasra.weather.ui.component.CardWeather

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.handleIntent(MapIntent.LoadListCityInfo)
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

        state.listCityInfo != null -> { // Display the map with a marker and weather information card
            MapContent(
                listCityInfo = state.listCityInfo!!,
                onInfoWindowClick = {viewModel.showCardWeather(it)},
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
                        weatherInfo = state.cityInfo!!,
                        backgroundColor = Color(0XFF88C0D0),
                        onClose = viewModel::hideCardWeather
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "MapScreen")
@Composable
private fun PreviewMapScreen() {
    MapScreen()
}


@Composable
fun MapContent(
    listCityInfo: List<CityInfo>,
    onInfoWindowClick: (CityInfo) -> Unit,
    onMapClick: (LatLng) -> Unit
) {

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(32.0, 53.0), 5f)
        },
        onMapClick = onMapClick
    ) {
        listCityInfo.forEach { cityInfo ->
            Marker(
                state = rememberMarkerState(position = LatLng(cityInfo.latitude, cityInfo.longitude)),
                title = cityInfo.city,
                snippet = cityInfo.description,
                onInfoWindowClick = { onInfoWindowClick(cityInfo) }
            )
        }

    }
}


