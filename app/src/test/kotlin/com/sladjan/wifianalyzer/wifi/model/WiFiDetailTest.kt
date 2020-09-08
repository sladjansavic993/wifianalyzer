
package com.sladjan.wifianalyzer.wifi.model

import com.sladjan.wifianalyzer.wifi.band.WiFiWidth
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class WiFiDetailTest {
    private val frequency = 2435
    private val level = -40
    private val vendorName = "VendorName"
    private val capabilities = "WPA-WPA2"
    private val ssid = "xyzSSID"
    private val bssid = "xyzBSSID"
    private val wiFiSignal = WiFiSignal(frequency, frequency, WiFiWidth.MHZ_20, level, true)
    private val wiFiAdditional = WiFiAdditional(vendorName, WiFiConnection.EMPTY)
    private val wiFiIdentifier = WiFiIdentifier(ssid, bssid)
    private val fixture = WiFiDetail(wiFiIdentifier, capabilities, wiFiSignal, wiFiAdditional)

    @Test
    fun testWiFiDetail() {
        // validate
        assertEquals(wiFiSignal, fixture.wiFiSignal)
        assertEquals(wiFiAdditional, fixture.wiFiAdditional)
        assertEquals(wiFiIdentifier, fixture.wiFiIdentifier)
        assertEquals(capabilities, fixture.capabilities)
        assertEquals(Security.WPA, fixture.security())
        assertEquals(setOf(Security.WPA, Security.WPA2), fixture.securities())
    }

    @Test
    fun testEquals() {
        // setup
        val other = WiFiDetail(wiFiIdentifier, capabilities, wiFiSignal)
        // execute & validate
        assertEquals(fixture, other)
        Assert.assertNotSame(fixture, other)
    }

    @Test
    fun testHashCode() {
        // setup
        val other = WiFiDetail(wiFiIdentifier, capabilities, wiFiSignal)
        // execute & validate
        assertEquals(fixture.hashCode(), other.hashCode())
    }

    @Test
    fun testCompareTo() {
        // setup
        val other = WiFiDetail(wiFiIdentifier, capabilities, wiFiSignal)
        // execute & validate
        assertEquals(0, fixture.compareTo(other))
    }

    @Test
    fun testWiFiDetailCopyConstructor() {
        // setup
        val expected = WiFiDetail(wiFiIdentifier, capabilities, wiFiSignal)
        // execute
        val actual = WiFiDetail(expected, expected.wiFiAdditional)
        // validate
        assertEquals(expected, actual)
        assertEquals(expected.wiFiIdentifier, actual.wiFiIdentifier)
        assertEquals(expected.capabilities, actual.capabilities)
        assertEquals(expected.security(), actual.security())
        assertEquals(expected.securities(), actual.securities())
        assertEquals(expected.wiFiAdditional, actual.wiFiAdditional)
        assertEquals(expected.wiFiSignal, actual.wiFiSignal)
    }

}