package me.jagdeep.playground.permission

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED

class PermissionChecker(private val context: Context) {

    fun hasLocationPermission(): Boolean {
        return context.checkSelfPermission(ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED
    }
}
