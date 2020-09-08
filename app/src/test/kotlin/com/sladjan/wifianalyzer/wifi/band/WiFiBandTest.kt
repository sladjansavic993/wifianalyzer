
package com.sladjan.wifianalyzer.wifi.band

import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.wifi.band.WiFiBand.Companion.find
import org.junit.Assert.*
import org.junit.Test

class WiFiBandTest {
    @Test
    fun testWiFiBand() {
        assertEquals(2, WiFiBand.values().size)
    }

    @Test
    fun testTextResource() {
        assertEquals(R.string.wifi_band_2ghz, WiFiBand.GHZ2.textResource)
        assertEquals(R.string.wifi_band_5ghz, WiFiBand.GHZ5.textResource)
    }

    @Test
    fun testToggle() {
        assertEquals(WiFiBand.GHZ5, WiFiBand.GHZ2.toggle())
        assertEquals(WiFiBand.GHZ2, WiFiBand.GHZ5.toggle())
    }

    @Test
    fun testGhz5() {
        assertFalse(WiFiBand.GHZ2.ghz5())
        assertTrue(WiFiBand.GHZ5.ghz5())
    }

    @Test
    fun testWiFiBandFind() {
        assertEquals(WiFiBand.GHZ2, find(2399))
        assertEquals(WiFiBand.GHZ2, find(2400))
        assertEquals(WiFiBand.GHZ2, find(2499))
        assertEquals(WiFiBand.GHZ2, find(2500))
        assertEquals(WiFiBand.GHZ2, find(4899))
        assertEquals(WiFiBand.GHZ5, find(4900))
        assertEquals(WiFiBand.GHZ5, find(5899))
        assertEquals(WiFiBand.GHZ2, find(5900))
    }
}