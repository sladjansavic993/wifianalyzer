
package com.sladjan.wifianalyzer.wifi.model

import com.sladjan.util.EMPTY
import org.junit.Assert.*
import org.junit.Test

class WiFiConnectionTest {
    private val ipAddress = "21.205.91.7"
    private val linkSpeed = 21
    private val wiFiIdentifier = WiFiIdentifier("SSID-123", "BSSID-123")
    private val fixture: WiFiConnection = WiFiConnection(wiFiIdentifier, ipAddress, linkSpeed)

    @Test
    fun testWiFiConnectionEmpty() {
        // validate
        assertEquals(WiFiIdentifier.EMPTY, WiFiConnection.EMPTY.wiFiIdentifier)
        assertEquals(String.EMPTY, WiFiConnection.EMPTY.ipAddress)
        assertEquals(WiFiConnection.LINK_SPEED_INVALID, WiFiConnection.EMPTY.linkSpeed)
        assertFalse(WiFiConnection.EMPTY.connected())
    }

    @Test
    fun testWiFiConnection() {
        // validate
        assertEquals(wiFiIdentifier, fixture.wiFiIdentifier)
        assertEquals(ipAddress, fixture.ipAddress)
        assertEquals(linkSpeed, fixture.linkSpeed)
        assertTrue(fixture.connected())
    }

    @Test
    fun testEquals() {
        // setup
        val wiFiIdentifier = WiFiIdentifier("SSID-123", "BSSID-123")
        val other = WiFiConnection(wiFiIdentifier, String.EMPTY, WiFiConnection.LINK_SPEED_INVALID)
        // execute & validate
        assertEquals(fixture, other)
        assertNotSame(fixture, other)
    }

    @Test
    fun testHashCode() {
        // setup
        val wiFiIdentifier = WiFiIdentifier("SSID-123", "BSSID-123")
        val other = WiFiConnection(wiFiIdentifier, String.EMPTY, WiFiConnection.LINK_SPEED_INVALID)
        // execute & validate
        assertEquals(fixture.hashCode(), other.hashCode())
    }

}