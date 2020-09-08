
package com.sladjan.wifianalyzer.wifi.band

import java.util.*

internal class WiFiChannelCountryGHZ5 {
    private val channelsSet1: SortedSet<Int> = sortedSetOf(36, 40, 44, 48, 52, 56, 60, 64)
    private val channelsSet2: SortedSet<Int> = sortedSetOf(100, 104, 108, 112, 116, 120, 124, 128, 132, 136, 140, 144)
    private val channelsSet3: SortedSet<Int> = sortedSetOf(149, 153, 157, 161, 165)
    private val channelsToExcludeCanada: SortedSet<Int> = sortedSetOf(120, 124, 128)
    private val channelsToExcludeIsrael: SortedSet<Int> = channelsSet2.union(channelsSet3).toSortedSet()
    private val channels: SortedSet<Int> = channelsSet1.union(channelsSet2).union(channelsSet3).toSortedSet()
    private val channelsToExclude: Map<String, SortedSet<Int>> = mapOf(
            "AU" to channelsToExcludeCanada,    // Australia
            "CA" to channelsToExcludeCanada,    // Canada
            "CN" to channelsSet2,               // China
            "IL" to channelsToExcludeIsrael,    // Israel
            "JP" to channelsSet3,               // Japan
            "KR" to channelsSet2,               // South Korea
            "TR" to channelsSet3,               // Turkey
            "ZA" to channelsSet3                // South Africa
    )

    fun findChannels(countryCode: String): SortedSet<Int> =
            channels.subtract(channelsToExclude[countryCode.capitalize()]?.toSortedSet()
                    ?: setOf())
                    .toSortedSet()

}