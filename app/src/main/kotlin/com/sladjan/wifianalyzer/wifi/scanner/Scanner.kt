
package com.sladjan.wifianalyzer.wifi.scanner

import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import com.sladjan.wifianalyzer.settings.Settings
import com.sladjan.wifianalyzer.wifi.model.WiFiData

internal class Scanner(private val wiFiManagerWrapper: WiFiManagerWrapper,
                       private val settings: Settings,
                       private val cache: Cache = Cache(),
                       private val transformer: Transformer = Transformer()) : ScannerService {
    private val updateNotifiers: MutableList<UpdateNotifier> = mutableListOf()
    private var wiFiData: WiFiData = WiFiData.EMPTY
    lateinit var periodicScan: PeriodicScan

    override fun update() {
        wiFiManagerWrapper.enableWiFi()
        scanResults()
        wiFiData = transformer.transformToWiFiData(cache.scanResults(), cache.wifiInfo())
        updateNotifiers.forEach { it.update(wiFiData) }
    }

    override fun wiFiData(): WiFiData = wiFiData

    override fun register(updateNotifier: UpdateNotifier): Boolean = updateNotifiers.add(updateNotifier)

    override fun unregister(updateNotifier: UpdateNotifier): Boolean = updateNotifiers.remove(updateNotifier)

    override fun pause(): Unit = periodicScan.stop()

    override fun running(): Boolean = periodicScan.running

    override fun resume(): Unit = periodicScan.start()

    override fun stop() {
        if (settings.wiFiOffOnExit()) {
            wiFiManagerWrapper.disableWiFi()
        }
    }

    override fun toggle(): Unit =
            if (periodicScan.running) {
                periodicScan.stop()
            } else {
                periodicScan.start()
            }

    fun registered(): Int = updateNotifiers.size

    private fun scanResults() {
        try {
            if (wiFiManagerWrapper.startScan()) {
                val scanResults: List<ScanResult> = wiFiManagerWrapper.scanResults()
                val wifiInfo: WifiInfo? = wiFiManagerWrapper.wiFiInfo()
                cache.add(scanResults, wifiInfo)
            }
        } catch (e: Exception) {
            // critical error: do not die
        }
    }

}