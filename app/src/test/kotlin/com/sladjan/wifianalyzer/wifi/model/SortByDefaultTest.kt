
package com.sladjan.wifianalyzer.wifi.model

import com.sladjan.util.EMPTY
import com.sladjan.wifianalyzer.wifi.band.WiFiWidth
import org.junit.Assert.assertEquals
import org.junit.Test

class SortByDefaultTest {
    private val fixture = sortByDefault()

    @Test
    fun testSortByDefault() {
        // setup
        val wiFiDetail1 = WiFiDetail(
                WiFiIdentifier("SSID1", "BSSID1"),
                String.EMPTY,
                WiFiSignal(2462, 2462, WiFiWidth.MHZ_20, -55, true),
                WiFiAdditional.EMPTY)
        val wiFiDetail2 = WiFiDetail(
                WiFiIdentifier("SSID1", "BSSID1"),
                String.EMPTY,
                WiFiSignal(2432, 2432, WiFiWidth.MHZ_40, -35, false),
                WiFiAdditional.EMPTY)
        // execute
        val actual: Int = fixture.compare(wiFiDetail1, wiFiDetail2)
        // validate
        assertEquals(0, actual)
    }

    @Test
    fun testSortByDefaultWithDifferentSSID() {
        // setup
        val wiFiDetail1 = WiFiDetail(
                WiFiIdentifier("ssid1", "BSSID1"),
                String.EMPTY,
                WiFiSignal(2462, 2462, WiFiWidth.MHZ_20, -55, true),
                WiFiAdditional.EMPTY)
        val wiFiDetail2 = WiFiDetail(
                WiFiIdentifier("SSID1", "BSSID1"),
                String.EMPTY,
                WiFiSignal(2462, 2462, WiFiWidth.MHZ_20, -55, true),
                WiFiAdditional.EMPTY)
        // execute
        val actual: Int = fixture.compare(wiFiDetail1, wiFiDetail2)
        // validate
        assertEquals(32, actual)
    }

    @Test
    fun testSortByDefaultWithDifferentBSSID() {
        // setup
        val wiFiDetail1 = WiFiDetail(
                WiFiIdentifier("SSID1", "bssid1"),
                String.EMPTY,
                WiFiSignal(2462, 2462, WiFiWidth.MHZ_20, -55, true),
                WiFiAdditional.EMPTY)
        val wiFiDetail2 = WiFiDetail(
                WiFiIdentifier("SSID1", "BSSID1"),
                String.EMPTY,
                WiFiSignal(2462, 2462, WiFiWidth.MHZ_20, -55, true),
                WiFiAdditional.EMPTY)
        // execute
        val actual: Int = fixture.compare(wiFiDetail1, wiFiDetail2)
        // validate
        assertEquals(32, actual)
    }
}