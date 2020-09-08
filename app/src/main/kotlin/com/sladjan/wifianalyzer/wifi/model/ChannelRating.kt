
package com.sladjan.wifianalyzer.wifi.model

import com.sladjan.annotation.OpenClass
import com.sladjan.wifianalyzer.wifi.band.WiFiChannel

@OpenClass
class ChannelRating {
    private val wiFiDetails: MutableList<WiFiDetail> = mutableListOf()

    fun count(wiFiChannel: WiFiChannel): Int = collectOverlapping(wiFiChannel).size

    fun strength(wiFiChannel: WiFiChannel): Strength =
            enumValues<Strength>()[
                    collectOverlapping(wiFiChannel)
                            .filter { !it.wiFiAdditional.wiFiConnection.connected() }
                            .map { it.wiFiSignal.strength().ordinal }
                            .maxBy { it } ?: Strength.ZERO.ordinal
            ]

    fun wiFiDetails(): List<WiFiDetail> = wiFiDetails

    fun wiFiDetails(wiFiDetails: List<WiFiDetail>) {
        this.wiFiDetails.clear()
        this.wiFiDetails.addAll(removeSame(wiFiDetails))
    }

    fun bestChannels(wiFiChannels: List<WiFiChannel>): List<ChannelAPCount> =
            wiFiChannels
                    .filter { bestChannel(it) }
                    .map { ChannelAPCount(it, count(it)) }
                    .sorted()

    private fun removeSame(wiFiDetails: List<WiFiDetail>): List<WiFiDetail> {
        val (left: List<WiFiDetail>, right: List<WiFiDetail>) = wiFiDetails.partition { BSSID_LENGTH == it.wiFiIdentifier.bssid.length }
        return left.distinctBy { it.toKey() }.plus(right).sortedWith(SortBy.STRENGTH.sort)
    }

    private fun collectOverlapping(wiFiChannel: WiFiChannel): List<WiFiDetail> =
            wiFiDetails.filter { it.wiFiSignal.inRange(wiFiChannel.frequency) }

    private fun bestChannel(it: WiFiChannel): Boolean {
        val strength: Strength = strength(it)
        return Strength.ZERO == strength || Strength.ONE == strength
    }

    private data class Key(val prefix: String, val bssid: String, val frequency: Int)

    private fun WiFiDetail.toKey(): Key = Key(
            this.wiFiIdentifier.bssid.substring(0, 0),
            this.wiFiIdentifier.bssid.substring(2, BSSID_LENGTH - 1),
            this.wiFiSignal.primaryFrequency)

    companion object {
        private const val BSSID_LENGTH = 17
    }

}