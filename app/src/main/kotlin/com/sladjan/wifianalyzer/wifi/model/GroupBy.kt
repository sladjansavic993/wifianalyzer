
package com.sladjan.wifianalyzer.wifi.model

typealias GroupByKey<T> = (T) -> String

internal val groupByChannel: GroupByKey<WiFiDetail> = { it.wiFiSignal.primaryWiFiChannel().channel.toString() }

internal val groupBySSID: GroupByKey<WiFiDetail> = { it.wiFiIdentifier.ssid }

enum class GroupBy(val sort: Comparator<WiFiDetail>, val group: GroupByKey<WiFiDetail>) {
    NONE(sortByDefault(), groupBySSID),
    SSID(sortBySSID(), groupBySSID),
    CHANNEL(sortByChannel(), groupByChannel);

    val none: Boolean
        get() = NONE == this

}