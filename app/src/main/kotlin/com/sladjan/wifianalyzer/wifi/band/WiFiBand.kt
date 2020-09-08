
package com.sladjan.wifianalyzer.wifi.band

import com.sladjan.wifianalyzer.R

enum class WiFiBand(val textResource: Int, val wiFiChannels: WiFiChannels) {
    GHZ2(R.string.wifi_band_2ghz, WiFiChannelsGHZ2()),
    GHZ5(R.string.wifi_band_5ghz, WiFiChannelsGHZ5());

    fun toggle(): WiFiBand = if (ghz5()) GHZ2 else GHZ5

    fun ghz5(): Boolean = GHZ5 == this

    companion object {
        fun find(frequency: Int): WiFiBand = values().find { it.wiFiChannels.inRange(frequency) }
                ?: GHZ2
    }

}