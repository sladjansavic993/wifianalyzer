
package com.sladjan.wifianalyzer.wifi.scanner

import android.annotation.TargetApi
import android.net.wifi.WifiManager
import android.os.Build
import com.sladjan.annotation.OpenClass
import com.sladjan.util.buildMinVersionQ
import com.sladjan.wifianalyzer.MainContext
import com.sladjan.wifianalyzer.startWiFiSettings

@OpenClass
internal class WiFiSwitch(private val wifiManager: WifiManager) {
    fun on(): Boolean = enable(true)

    fun off(): Boolean = enable(false)

    fun startWiFiSettings(): Unit = MainContext.INSTANCE.mainActivity.startWiFiSettings()

    fun minVersionQ(): Boolean = buildMinVersionQ()

    private fun enable(enabled: Boolean): Boolean = if (minVersionQ()) enableWiFiAndroidQ() else enableWiFiLegacy(enabled)

    @TargetApi(Build.VERSION_CODES.Q)
    private fun enableWiFiAndroidQ(): Boolean {
        startWiFiSettings()
        return true
    }

    @Suppress("DEPRECATION")
    private fun enableWiFiLegacy(enabled: Boolean): Boolean = wifiManager.setWifiEnabled(enabled)

}