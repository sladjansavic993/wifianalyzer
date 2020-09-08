//SS
package com.sladjan.wifianalyzer.wifi.timegraph

import com.sladjan.annotation.OpenClass
import com.sladjan.wifianalyzer.wifi.graphutils.MAX_NOT_SEEN_COUNT
import com.sladjan.wifianalyzer.wifi.model.WiFiDetail

@OpenClass
internal class TimeGraphCache {
    private val notSeen: MutableMap<WiFiDetail, Int> = mutableMapOf()

    fun active(): Set<WiFiDetail> =
            notSeen.filterValues { it <= MAX_NOT_SEEN_COUNT }
                    .keys
                    .toSet()

    fun clear() =
            notSeen.filterValues { it > MAX_NOT_SEEN_COUNT }
                    .keys
                    .forEach { notSeen.remove(it) }

    fun add(wiFiDetail: WiFiDetail) {
        notSeen[wiFiDetail] = (notSeen[wiFiDetail] ?: 0) + 1
    }

    fun reset(wiFiDetail: WiFiDetail) {
        if (notSeen[wiFiDetail] != null) notSeen[wiFiDetail] = 0
    }

    val wiFiDetails: Set<WiFiDetail>
        get() = notSeen.keys

}