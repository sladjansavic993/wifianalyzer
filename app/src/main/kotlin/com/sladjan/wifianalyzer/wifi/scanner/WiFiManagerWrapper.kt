//SS
package com.sladjan.wifianalyzer.wifi.scanner

import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import com.sladjan.annotation.OpenClass

@OpenClass
internal class WiFiManagerWrapper(private val wifiManager: WifiManager, private val wiFiSwitch: WiFiSwitch = WiFiSwitch(wifiManager)) {
    fun wiFiEnabled(): Boolean =
            try {
                wifiManager.isWifiEnabled
            } catch (e: Exception) {
                false
            }

    fun enableWiFi(): Boolean =
            try {
                wiFiEnabled() || wiFiSwitch.on()
            } catch (e: Exception) {
                false
            }

    fun disableWiFi(): Boolean =
            try {
                !wiFiEnabled() || wiFiSwitch.off()
            } catch (e: Exception) {
                false
            }

    @Suppress("DEPRECATION")
    fun startScan(): Boolean =
            try {
                wifiManager.startScan()
            } catch (e: Exception) {
                false
            }

    fun scanResults(): List<ScanResult> =
            try {
                wifiManager.scanResults ?: listOf()
            } catch (e: Exception) {
                listOf()
            }

    fun wiFiInfo(): WifiInfo? =
            try {
                wifiManager.connectionInfo
            } catch (e: Exception) {
                null
            }

}

