
package com.sladjan.wifianalyzer.wifi.band

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*

class WiFiChannelCountryGHZ2Test {
    private val channelsSet1: SortedSet<Int> = sortedSetOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
    private val channelsSet2: SortedSet<Int> = sortedSetOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)
    private val fixture = WiFiChannelCountryGHZ2()

    @Test
    fun testChannelsForUSAndSimilar() {
        listOf("AS", "CA", "CO", "DO", "FM", "GT", "GU", "MP", "MX", "PA", "PR", "UM", "US", "UZ", "VI")
                .forEach { validateChannels(channelsSet1, fixture.findChannels(it)) }
    }

    @Test
    fun testChannelsForWorld() {
        listOf("GB", "XYZ", "AU", "AE")
                .forEach { validateChannels(channelsSet2, fixture.findChannels(it)) }
    }

    private fun validateChannels(expected: SortedSet<Int>, actual: SortedSet<Int>) {
        assertEquals(expected.size, actual.size)
        assertTrue(actual.containsAll(expected))
    }

}