
package com.sladjan.wifianalyzer.wifi.band

import com.sladjan.util.allCountries
import com.sladjan.util.findByCountryCode
import java.util.*

class WiFiChannelCountry(private val country: Locale) {
    private val unknown = "-Unknown"
    private val wiFiChannelGHZ2 = WiFiChannelCountryGHZ2()
    private val wiFiChannelGHZ5 = WiFiChannelCountryGHZ5()

    fun countryCode(): String = country.country

    fun countryName(currentLocale: Locale): String {
        val countryName: String = country.getDisplayCountry(currentLocale)
        return if (country.country == countryName) countryName + unknown else countryName
    }

    fun channelsGHZ2(): SortedSet<Int> = wiFiChannelGHZ2.findChannels(country.country)

    fun channelsGHZ5(): SortedSet<Int> = wiFiChannelGHZ5.findChannels(country.country)

    fun channelAvailableGHZ2(channel: Int): Boolean = channelsGHZ2().contains(channel)

    fun channelAvailableGHZ5(channel: Int): Boolean = channelsGHZ5().contains(channel)

    companion object {
        fun find(countryCode: String): WiFiChannelCountry = WiFiChannelCountry(findByCountryCode(countryCode))

        fun findAll(): List<WiFiChannelCountry> = allCountries().map { WiFiChannelCountry(it) }
    }

}