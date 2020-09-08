
package com.sladjan.wifianalyzer

import com.sladjan.annotation.OpenClass
import com.sladjan.wifianalyzer.wifi.band.WiFiChannels

const val SIZE_MIN = 1024
const val SIZE_MAX = 4096

@OpenClass
class Configuration(val largeScreen: Boolean) {
    var size = SIZE_MAX

    var wiFiChannelPair = WiFiChannels.UNKNOWN

    val sizeAvailable: Boolean
        get() = size == SIZE_MAX

}