
package com.sladjan.wifianalyzer.wifi.scanner

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Handler
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.settings.Settings
import com.sladjan.wifianalyzer.wifi.model.WiFiData

interface ScannerService {
    fun update()
    fun wiFiData(): WiFiData
    fun register(updateNotifier: UpdateNotifier): Boolean
    fun unregister(updateNotifier: UpdateNotifier): Boolean
    fun pause()
    fun running(): Boolean
    fun resume()
    fun stop()
    fun toggle()
}

fun makeScannerService(mainActivity: MainActivity, handler: Handler, settings: Settings): ScannerService {
    val scanner = Scanner(mainActivity.wiFiManagerWrapper(), settings)
    scanner.periodicScan = PeriodicScan(scanner, handler, settings)
    scanner.resume()
    return scanner
}

internal fun MainActivity.wiFiManager(): WifiManager = this.applicationContext.wiFiManager()

internal fun Context.wiFiManager(): WifiManager = this.getSystemService(Context.WIFI_SERVICE) as WifiManager

internal fun MainActivity.wiFiManagerWrapper(): WiFiManagerWrapper = WiFiManagerWrapper(this.wiFiManager())
