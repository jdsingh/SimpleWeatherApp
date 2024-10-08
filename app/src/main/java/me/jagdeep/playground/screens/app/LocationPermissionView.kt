@file:OptIn(ExperimentalPermissionsApi::class)

package me.jagdeep.playground.screens.app

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import me.jagdeep.playground.R

@Composable
fun LocationPermissionView(locationGranted: () -> Unit) {
    val locationPermissionState = rememberPermissionState(ACCESS_COARSE_LOCATION)

    if (locationPermissionState.status.isGranted) {
        locationGranted()
    } else {
        Card(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                if (locationPermissionState.status.shouldShowRationale) {
                    // If the user has denied the permission but the rationale can be shown,
                    // then gently explain why the app requires this permission
                    Text(text = stringResource(id = R.string.location_rationale))
                } else {
                    // If it's the first time the user lands on this feature, or the user
                    // doesn't want to be asked again for this permission, explain that the
                    // permission is required
                    Text(text = stringResource(id = R.string.location_request))
                }

                Spacer(modifier = Modifier.size(12.dp))

                OutlinedButton(onClick = { locationPermissionState.launchPermissionRequest() }) {
                    Text(text = stringResource(id = R.string.btn_request_location))
                }
            }
        }
    }
}
