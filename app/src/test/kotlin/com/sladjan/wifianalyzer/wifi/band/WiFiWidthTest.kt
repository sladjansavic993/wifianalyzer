//SS
package com.sladjan.wifianalyzer.wifi.band

import org.junit.Assert.assertEquals
import org.junit.Test

class WiFiWidthTest {
    @Test
    fun testWiFiWidth() {
        assertEquals(5, WiFiWidth.values().size)
    }

    @Test
    fun testFrequencyWidth() {
        assertEquals(20, WiFiWidth.MHZ_20.frequencyWidth)
        assertEquals(40, WiFiWidth.MHZ_40.frequencyWidth)
        assertEquals(80, WiFiWidth.MHZ_80.frequencyWidth)
        assertEquals(160, WiFiWidth.MHZ_160.frequencyWidth)
        assertEquals(80, WiFiWidth.MHZ_80_PLUS.frequencyWidth)
    }

    @Test
    fun testFrequencyHalfWidth() {
        assertEquals(10, WiFiWidth.MHZ_20.frequencyWidthHalf)
        assertEquals(20, WiFiWidth.MHZ_40.frequencyWidthHalf)
        assertEquals(40, WiFiWidth.MHZ_80.frequencyWidthHalf)
        assertEquals(80, WiFiWidth.MHZ_160.frequencyWidthHalf)
        assertEquals(40, WiFiWidth.MHZ_80_PLUS.frequencyWidthHalf)
    }
}