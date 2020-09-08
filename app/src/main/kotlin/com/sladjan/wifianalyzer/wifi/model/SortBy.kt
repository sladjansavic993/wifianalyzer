
package com.sladjan.wifianalyzer.wifi.model

fun sortBySSID(): Comparator<WiFiDetail> =
        compareBy<WiFiDetail> { it.wiFiIdentifier.ssid }
                .thenByDescending { it.wiFiSignal.level }
                .thenBy { it.wiFiIdentifier.bssid }

fun sortByStrength(): Comparator<WiFiDetail> =
        compareByDescending<WiFiDetail> { it.wiFiSignal.level }
                .thenBy { it.wiFiIdentifier.ssid }
                .thenBy { it.wiFiIdentifier.bssid }

fun sortByChannel(): Comparator<WiFiDetail> =
        compareBy<WiFiDetail> { it.wiFiSignal.primaryWiFiChannel().channel }
                .thenByDescending { it.wiFiSignal.level }
                .thenBy { it.wiFiIdentifier.ssid }
                .thenBy { it.wiFiIdentifier.bssid }

fun sortByDefault(): Comparator<WiFiDetail> =
        compareBy<WiFiDetail> { it.wiFiIdentifier.ssid }.thenBy { it.wiFiIdentifier.bssid }


enum class SortBy(val sort: Comparator<WiFiDetail>) {
    STRENGTH(sortByStrength()),
    SSID(sortBySSID()),
    CHANNEL(sortByChannel());
}