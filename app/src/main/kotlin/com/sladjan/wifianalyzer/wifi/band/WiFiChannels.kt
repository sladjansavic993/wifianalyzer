
package com.sladjan.wifianalyzer.wifi.band

typealias WiFiRange = Pair<Int, Int>
typealias WiFiChannelPair = Pair<WiFiChannel, WiFiChannel>

fun WiFiChannelPair.channelCount(): Int = second.channel - first.channel + 1 + WiFiChannels.CHANNEL_OFFSET * 2

abstract class WiFiChannels(private val wiFiRange: WiFiRange, private val wiFiChannelPairs: List<WiFiChannelPair>) {
    fun inRange(frequency: Int): Boolean =
            frequency >= wiFiRange.first && frequency <= wiFiRange.second

    fun wiFiChannelByFrequency(frequency: Int): WiFiChannel =
            if (inRange(frequency)) {
                wiFiChannelPairs.find { WiFiChannel.UNKNOWN != wiFiChannel(frequency, it) }
                        ?.let { wiFiChannel(frequency, it) }
                        ?: WiFiChannel.UNKNOWN
            } else {
                WiFiChannel.UNKNOWN
            }

    fun wiFiChannelByChannel(channel: Int): WiFiChannel =
            wiFiChannelPairs.find { channel >= it.first.channel && channel <= it.second.channel }
                    ?.let { WiFiChannel(channel, it.first.frequency + (channel - it.first.channel) * FREQUENCY_SPREAD) }
                    ?: WiFiChannel.UNKNOWN

    fun wiFiChannelFirst(): WiFiChannel = wiFiChannelPairs[0].first

    fun wiFiChannelLast(): WiFiChannel = wiFiChannelPairs[wiFiChannelPairs.size - 1].second

    fun wiFiChannel(frequency: Int, wiFiChannelPair: WiFiChannelPair): WiFiChannel {
        val first: WiFiChannel = wiFiChannelPair.first
        val last: WiFiChannel = wiFiChannelPair.second
        val channel: Int = ((frequency - first.frequency).toDouble() / FREQUENCY_SPREAD + first.channel + 0.5).toInt()
        return if (channel >= first.channel && channel <= last.channel)
            WiFiChannel(channel, frequency)
        else
            WiFiChannel.UNKNOWN
    }

    abstract fun availableChannels(countryCode: String): List<WiFiChannel>
    abstract fun channelAvailable(countryCode: String, channel: Int): Boolean
    abstract fun wiFiChannelPairs(): List<WiFiChannelPair>
    abstract fun wiFiChannelPairFirst(countryCode: String): WiFiChannelPair
    abstract fun wiFiChannelByFrequency(frequency: Int, wiFiChannelPair: WiFiChannelPair): WiFiChannel

    fun availableChannels(channels: Set<Int>): List<WiFiChannel> = channels.map { this.wiFiChannelByChannel(it) }

    fun wiFiChannels(): List<WiFiChannel> = wiFiChannelPairs.flatMap { transform(it) }

    private fun transform(wiFiChannelPair: WiFiChannelPair): List<WiFiChannel> =
            (wiFiChannelPair.first.channel..wiFiChannelPair.second.channel).map { wiFiChannelByChannel(it) }

    companion object {
        val UNKNOWN = WiFiChannelPair(WiFiChannel.UNKNOWN, WiFiChannel.UNKNOWN)
        internal const val FREQUENCY_SPREAD = 5
        internal const val CHANNEL_OFFSET = 2
        internal const val FREQUENCY_OFFSET = FREQUENCY_SPREAD * CHANNEL_OFFSET
    }

}