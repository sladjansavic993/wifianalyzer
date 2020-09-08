//SS
package com.sladjan.wifianalyzer.wifi.band

import com.sladjan.wifianalyzer.wifi.band.WiFiChannelCountry.Companion.find
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class WiFiChannelCountryTest {
    @Test
    fun testChannelAvailableWithTrue() {
        assertTrue(find(Locale.US.country).channelAvailableGHZ2(1))
        assertTrue(find(Locale.US.country).channelAvailableGHZ2(11))
        assertTrue(find(Locale.US.country).channelAvailableGHZ5(36))
        assertTrue(find(Locale.US.country).channelAvailableGHZ5(165))
        assertTrue(find(Locale.UK.country).channelAvailableGHZ2(1))
        assertTrue(find(Locale.UK.country).channelAvailableGHZ2(13))
        assertTrue(find(Locale.UK.country).channelAvailableGHZ5(36))
        assertTrue(find(Locale.UK.country).channelAvailableGHZ5(140))
    }

    @Test
    fun testChannelAvailableWithGHZ2() {
        assertFalse(find(Locale.US.country).channelAvailableGHZ2(0))
        assertFalse(find(Locale.US.country).channelAvailableGHZ2(12))
        assertFalse(find(Locale.UK.country).channelAvailableGHZ2(0))
        assertFalse(find(Locale.UK.country).channelAvailableGHZ2(14))
    }

    @Test
    fun testChannelAvailableWithGHZ5() {
        assertTrue(find(Locale.US.country).channelAvailableGHZ5(36))
        assertTrue(find(Locale.US.country).channelAvailableGHZ5(165))
        assertTrue(find(Locale.UK.country).channelAvailableGHZ5(36))
        assertTrue(find(Locale.UK.country).channelAvailableGHZ5(140))
        assertTrue(find("AE").channelAvailableGHZ5(36))
        assertTrue(find("AE").channelAvailableGHZ5(64))
    }

    @Test
    fun testFindCorrectlyPopulatesGHZ() {
        // setup
        val expectedCountryCode = Locale.US.country
        val expectedGHZ2: Set<Int> = WiFiChannelCountryGHZ2().findChannels(expectedCountryCode)
        val expectedGHZ5: Set<Int> = WiFiChannelCountryGHZ5().findChannels(expectedCountryCode)
        // execute
        val actual: WiFiChannelCountry = find(expectedCountryCode)
        // validate
        assertEquals(expectedCountryCode, actual.countryCode())
        assertArrayEquals(expectedGHZ2.toTypedArray(), actual.channelsGHZ2().toTypedArray())
        assertArrayEquals(expectedGHZ5.toTypedArray(), actual.channelsGHZ5().toTypedArray())
    }

    @Test
    fun testFindCorrectlyPopulatesCountryCodeAndName() {
        // setup
        val expected = Locale.SIMPLIFIED_CHINESE
        val expectedCountryCode = expected.country
        // execute
        val actual: WiFiChannelCountry = find(expectedCountryCode)
        // validate
        assertEquals(expectedCountryCode, actual.countryCode())
        assertNotEquals(expected.displayCountry, actual.countryName(expected))
        assertEquals(expected.getDisplayCountry(expected), actual.countryName(expected))
    }
}