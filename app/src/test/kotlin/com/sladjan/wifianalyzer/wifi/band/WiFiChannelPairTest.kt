

package com.sladjan.wifianalyzer.wifi.band

import org.junit.Assert.assertEquals
import org.junit.Test

class WiFiChannelPairTest {

    @Test
    fun testChannelCount() {
        // setup
        val expected = 20 - 10 + 5
        val first = WiFiChannel(10, 10)
        val second = WiFiChannel(20, 20)
        val fixture = WiFiChannelPair(first, second)
        // execute
        val actual = fixture.channelCount()
        // validate
        assertEquals(expected, actual)
    }
}