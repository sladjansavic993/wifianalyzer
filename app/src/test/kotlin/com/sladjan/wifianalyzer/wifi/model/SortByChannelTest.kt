//SS
package com.sladjan.wifianalyzer.wifi.model

import com.sladjan.util.EMPTY
import com.sladjan.wifianalyzer.wifi.band.WiFiWidth
import org.junit.Assert
import org.junit.Test

class SortByChannelTest {
    private val fixture = sortByChannel()

    @Test
    fun testSortByChannel() {
        // setup
        val wiFiDetail1 = WiFiDetail(
                WiFiIdentifier("SSID1", "BSSID1"),
                String.EMPTY,
                WiFiSignal(2462, 2462, WiFiWidth.MHZ_20, -55, true),
                WiFiAdditional.EMPTY)
        val wiFiDetail2 = WiFiDetail(
                WiFiIdentifier("SSID1", "BSSID1"),
                String.EMPTY,
                WiFiSignal(2462, 2432, WiFiWidth.MHZ_40, -55, false),
                WiFiAdditional.EMPTY)
        // execute
        val actual: Int = fixture.compare(wiFiDetail1, wiFiDetail2)
        // validate
        Assert.assertEquals(0, actual)
    }

    @Test
    fun testSortByChannelWithDifferentChannel() {
        // setup
        val wiFiDetail1 = WiFiDetail(
                WiFiIdentifier("SSID1", "BSSID1"),
                String.EMPTY,
                WiFiSignal(2462, 2462, WiFiWidth.MHZ_20, -55, true),
                WiFiAdditional.EMPTY)
        val wiFiDetail2 = WiFiDetail(
                WiFiIdentifier("SSID1", "BSSID1"),
                String.EMPTY,
                WiFiSignal(2432, 2462, WiFiWidth.MHZ_20, -55, true),
                WiFiAdditional.EMPTY)
        // execute
        val actual: Int = fixture.compare(wiFiDetail1, wiFiDetail2)
        // validate
        Assert.assertEquals(1, actual)
    }

    @Test
    fun testSortByChannelWithDifferentSSID() {
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
        Assert.assertEquals(32, actual)
    }

    @Test
    fun testSortByChannelWithDifferentBSSID() {
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
        Assert.assertEquals(32, actual)
    }

    @Test
    fun testSortByChannelWithDifferentStrength() {
        // setup
        val wiFiDetail1 = WiFiDetail(
                WiFiIdentifier("SSID1", "BSSID1"),
                String.EMPTY,
                WiFiSignal(2462, 2462, WiFiWidth.MHZ_20, -55, true),
                WiFiAdditional.EMPTY)
        val wiFiDetail2 = WiFiDetail(
                WiFiIdentifier("SSID1", "BSSID1"),
                String.EMPTY,
                WiFiSignal(2462, 2462, WiFiWidth.MHZ_20, -35, true),
                WiFiAdditional.EMPTY)
        // execute
        val actual: Int = fixture.compare(wiFiDetail1, wiFiDetail2)
        // validate
        Assert.assertEquals(1, actual)
    }
}