package me.jagdeep.playground.permission

import android.location.Location
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await

class GetLastKnownLocationUseCase(private val locationClient: FusedLocationProviderClient) {

    @RequiresPermission("android.permission.ACCESS_COARSE_LOCATION")
    suspend fun getLastKnownLocation(): Location? {
        return locationClient.lastLocation.await()
    }
}
