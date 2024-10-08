package me.jagdeep.playground.permission

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await

class GetLastKnownLocationUseCase(private val locationClient: FusedLocationProviderClient) {

    @SuppressLint("MissingPermission")
    suspend fun getLastKnownLocation(): Location? {
        return locationClient.lastLocation.await()
    }
}
