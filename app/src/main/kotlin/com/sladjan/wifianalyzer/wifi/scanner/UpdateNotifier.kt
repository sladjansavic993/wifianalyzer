
package com.sladjan.wifianalyzer.wifi.scanner

import com.sladjan.wifianalyzer.wifi.model.WiFiData

interface UpdateNotifier {
    fun update(wiFiData: WiFiData)
}