
package com.sladjan.wifianalyzer.wifi.model

import com.sladjan.util.EMPTY

class WiFiAdditional(val vendorName: String = String.EMPTY,
                     val wiFiConnection: WiFiConnection = WiFiConnection.EMPTY) {

    companion object {
        val EMPTY = WiFiAdditional()
    }

}