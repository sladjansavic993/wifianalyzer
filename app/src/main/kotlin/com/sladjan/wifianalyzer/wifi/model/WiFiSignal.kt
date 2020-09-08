//SS
package com.sladjan.wifianalyzer.wifi.model

import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.band.WiFiChannel
import com.sladjan.wifianalyzer.wifi.band.WiFiWidth

data class WiFiSignal(val primaryFrequency: Int = 0,
                      val centerFrequency: Int = 0,
                      val wiFiWidth: WiFiWidth = WiFiWidth.MHZ_20,
                      val level: Int = 0,
                      val is80211mc: Boolean = false) {

    val wiFiBand: WiFiBand = WiFiBand.find(primaryFrequency)

    fun frequencyStart(): Int = centerFrequency - wiFiWidth.frequencyWidthHalf

    fun frequencyEnd(): Int = centerFrequency + wiFiWidth.frequencyWidthHalf

    fun primaryWiFiChannel(): WiFiChannel = wiFiBand.wiFiChannels.wiFiChannelByFrequency(primaryFrequency)

    fun centerWiFiChannel(): WiFiChannel = wiFiBand.wiFiChannels.wiFiChannelByFrequency(centerFrequency)

    fun strength(): Strength = Strength.calculate(level)

    fun distance(): String {
        val distance: Double = calculateDistance(primaryFrequency, level)
        return String.format("~%.1fm", distance)
    }

    fun inRange(frequency: Int): Boolean {
        return frequency >= frequencyStart() && frequency <= frequencyEnd()
    }

    fun channelDisplay(): String {
        val primaryChannel: Int = primaryWiFiChannel().channel
        val centerChannel: Int = centerWiFiChannel().channel
        val channel: String = primaryChannel.toString()
        return if (primaryChannel != centerChannel) "$channel($centerChannel)" else channel
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as WiFiSignal
        if (primaryFrequency != other.primaryFrequency) return false
        if (wiFiWidth != other.wiFiWidth) return false
        return true
    }

    override fun hashCode(): Int = 31 * primaryFrequency + wiFiWidth.hashCode()

    companion object {
        const val FREQUENCY_UNITS = "MHz"

        val EMPTY = WiFiSignal()
    }

}