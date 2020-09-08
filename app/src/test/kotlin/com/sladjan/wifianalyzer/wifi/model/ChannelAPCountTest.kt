
package com.sladjan.wifianalyzer.wifi.model

import com.sladjan.wifianalyzer.wifi.band.WiFiChannel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotSame
import org.junit.Test

class ChannelAPCountTest {
    private val frequency = 2435
    private val channel = 10
    private val count = 111
    private val wiFiChannel: WiFiChannel = WiFiChannel(channel, frequency)
    private val fixture: ChannelAPCount = ChannelAPCount(wiFiChannel, count)

    @Test
    fun testEquals() {
        // setup
        val wiFiChannel = WiFiChannel(channel, frequency)
        val other = ChannelAPCount(wiFiChannel, count)
        // execute & validate
        assertEquals(fixture, other)
        assertNotSame(fixture, other)
    }

    @Test
    fun testHashCode() {
        // setup
        val wiFiChannel = WiFiChannel(channel, frequency)
        val other = ChannelAPCount(wiFiChannel, count)
        // execute & validate
        assertEquals(fixture.hashCode(), other.hashCode())
    }

    @Test
    fun testCompareTo() {
        // setup
        val wiFiChannel = WiFiChannel(channel, frequency)
        val other = ChannelAPCount(wiFiChannel, count)
        // execute & validate
        assertEquals(0, fixture.compareTo(other))
    }

}