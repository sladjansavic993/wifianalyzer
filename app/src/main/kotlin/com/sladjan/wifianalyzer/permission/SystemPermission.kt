//SS
package com.sladjan.wifianalyzer.permission

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.location.LocationManager
import android.os.Build
import com.sladjan.annotation.OpenClass
import com.sladjan.util.buildMinVersionM
import com.sladjan.util.buildMinVersionP

@OpenClass
class SystemPermission(private val activity: Activity) {
    fun enabled(): Boolean = !buildMinVersionM() || providerEnabledAndroidM()

    @TargetApi(Build.VERSION_CODES.M)
    private fun providerEnabledAndroidM(): Boolean =
            try {
                val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                locationEnabled(locationManager) || networkProviderEnabled(locationManager) || gpsProviderEnabled(locationManager)
            } catch (e: Exception) {
                false
            }

    private fun gpsProviderEnabled(locationManager: LocationManager): Boolean =
            try {
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            } catch (e: Exception) {
                false
            }

    private fun networkProviderEnabled(locationManager: LocationManager): Boolean =
            try {
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            } catch (e: Exception) {
                false
            }

    private fun locationEnabled(locationManager: LocationManager): Boolean =
            buildMinVersionP() && locationEnabledAndroidP(locationManager)

    @TargetApi(Build.VERSION_CODES.P)
    private fun locationEnabledAndroidP(locationManager: LocationManager): Boolean =
            try {
                locationManager.isLocationEnabled
            } catch (e: Exception) {
                false
            }

}