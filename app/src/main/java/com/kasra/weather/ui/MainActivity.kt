/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kasra.weather.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.kasra.weather.data.location.LocationTracker
import com.kasra.weather.ui.map.MapScreen
import dagger.hilt.android.AndroidEntryPoint
import com.kasra.weather.ui.theme.MyApplicationTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            RequestLocationPermission(
                onPermissionGranted = {
                    Toast.makeText(
                        this@MainActivity,
                        "Location permission granted",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onPermissionDenied = {
                    // Some requested permissions are denied.
                    // TODO: Implement appropriate action based on the user's decision.
                    Toast.makeText(
                        this@MainActivity,
                        "Location permission denied",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onPermissionsRevoked = {
                    // Previously granted permissions are revoked.
                    // TODO: Implement appropriate action based on the user's decision.
                    Toast.makeText(
                        this@MainActivity,
                        "Location permissions revoked",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (areLocationPermissionsGranted()) {
                        MapScreen()
                    }
                }
            }
        }

    }

    /**
     * Composable function to request location permissions and handle different scenarios.
     *
     * @param onPermissionGranted Callback to be executed when all requested permissions are granted.
     * @param onPermissionDenied Callback to be executed when any requested permission is denied.
     * @param onPermissionsRevoked Callback to be executed when previously granted permissions are revoked.
     */
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun RequestLocationPermission(
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit,
        onPermissionsRevoked: () -> Unit
    ) {
        // Initialize the state for managing multiple location permissions.
        val permissionState = rememberMultiplePermissionsState(
            listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        )

        // Use LaunchedEffect to handle permissions logic when the composition is launched.
        LaunchedEffect(key1 = permissionState) {
            // Check if all previously granted permissions are revoked.
            val allPermissionsRevoked =
                permissionState.permissions.size == permissionState.revokedPermissions.size

            // Filter permissions that need to be requested.
            val permissionsToRequest = permissionState.permissions.filter {
                !it.status.isGranted
            }

            // If there are permissions to request, launch the permission request.
            if (permissionsToRequest.isNotEmpty()) permissionState.launchMultiplePermissionRequest()

            // Execute callbacks based on permission status.
            if (allPermissionsRevoked) {
                onPermissionsRevoked()
            } else {
                if (permissionState.allPermissionsGranted) {
                    onPermissionGranted()
                } else {
                    onPermissionDenied()
                }
            }
        }
    }

    private fun areLocationPermissionsGranted(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }

}

