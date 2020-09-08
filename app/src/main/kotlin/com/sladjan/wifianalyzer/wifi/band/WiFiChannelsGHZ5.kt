
package com.sladjan.wifianalyzer.wifi.band

class WiFiChannelsGHZ5 : WiFiChannels(RANGE, SETS) {
    override fun wiFiChannelPairs(): List<WiFiChannelPair> = SETS

    override fun wiFiChannelPairFirst(countryCode: String): WiFiChannelPair =
            if (countryCode.isBlank())
                SET1
            else
                wiFiChannelPairs().find { channelAvailable(countryCode, it.first.channel) }
                        ?: SET1

    override fun availableChannels(countryCode: String): List<WiFiChannel> =
            availableChannels(WiFiChannelCountry.find(countryCode).channelsGHZ5())

    override fun channelAvailable(countryCode: String, channel: Int): Boolean =
            WiFiChannelCountry.find(countryCode).channelAvailableGHZ5(channel)

    override fun wiFiChannelByFrequency(frequency: Int, wiFiChannelPair: WiFiChannelPair): WiFiChannel =
            if (inRange(frequency)) wiFiChannel(frequency, wiFiChannelPair) else WiFiChannel.UNKNOWN

    companion object {
        val SET1 = WiFiChannelPair(WiFiChannel(36, 5180), WiFiChannel(64, 5320))
        val SET2 = WiFiChannelPair(WiFiChannel(100, 5500), WiFiChannel(144, 5720))
        val SET3 = WiFiChannelPair(WiFiChannel(149, 5745), WiFiChannel(165, 5825))
        val SETS = listOf(SET1, SET2, SET3)
        private val RANGE = WiFiRange(4900, 5899)
    }
}