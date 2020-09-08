
package com.sladjan.wifianalyzer.wifi.band

enum class WiFiWidth(val frequencyWidth: Int) {
    MHZ_20(20),
    MHZ_40(40),
    MHZ_80(80),
    MHZ_160(160),
    MHZ_80_PLUS(80);

    val frequencyWidthHalf: Int = frequencyWidth / 2

}