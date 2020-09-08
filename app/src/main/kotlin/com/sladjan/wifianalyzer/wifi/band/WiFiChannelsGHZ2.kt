
package com.sladjan.wifianalyzer.wifi.band

class WiFiChannelsGHZ2 : WiFiChannels(RANGE, SETS) {
    override fun wiFiChannelPairs(): List<WiFiChannelPair> = listOf(SET)

    override fun wiFiChannelPairFirst(countryCode: String): WiFiChannelPair = SET

    override fun availableChannels(countryCode: String): List<WiFiChannel> =
            availableChannels(WiFiChannelCountry.find(countryCode).channelsGHZ2())

    override fun channelAvailable(countryCode: String, channel: Int): Boolean =
            WiFiChannelCountry.find(countryCode).channelAvailableGHZ2(channel)

    override fun wiFiChannelByFrequency(frequency: Int, wiFiChannelPair: WiFiChannelPair): WiFiChannel =
            if (inRange(frequency)) wiFiChannel(frequency, SET) else WiFiChannel.UNKNOWN

    companion object {
        private val RANGE: WiFiRange = WiFiRange(2400, 2499)
        private val SETS: List<WiFiChannelPair> = listOf(
                WiFiChannelPair(WiFiChannel(1, 2412), WiFiChannel(13, 2472)),
                WiFiChannelPair(WiFiChannel(14, 2484), WiFiChannel(14, 2484)))
        private val SET: WiFiChannelPair = WiFiChannelPair(SETS[0].first, SETS[SETS.size - 1].second)
    }
}