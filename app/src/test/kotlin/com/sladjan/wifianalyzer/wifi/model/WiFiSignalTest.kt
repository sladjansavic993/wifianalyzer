
package com.sladjan.wifianalyzer.wifi.model

import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.band.WiFiWidth
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class WiFiSignalTest {
    private val primaryFrequency = 2432
    private val primaryChannel = 5
    private val centerFrequency = 2437
    private val centerChannel = 6
    private val level = -65
    private val fixture: WiFiSignal = WiFiSignal(primaryFrequency, centerFrequency, WiFiWidth.MHZ_40, level, true)

    @Test
    fun testWiFiFrequency() {
        // validate
        assertEquals(primaryFrequency, fixture.primaryFrequency)
        assertEquals(centerFrequency, fixture.centerFrequency)
        assertEquals(level, fixture.level)
        assertEquals(WiFiBand.GHZ2, fixture.wiFiBand)
        assertEquals(WiFiWidth.MHZ_40, fixture.wiFiWidth)
    }

    @Test
    fun testWiFiFrequencyWithFrequencyAndWiFiWidth() {
        // execute
        val fixture = WiFiSignal(primaryFrequency, centerFrequency, WiFiWidth.MHZ_80, level, true)
        // validate
        assertEquals(primaryFrequency, fixture.primaryFrequency)
        assertEquals(primaryChannel, fixture.primaryWiFiChannel().channel)
        assertEquals(centerFrequency, fixture.centerFrequency)
        assertEquals(centerChannel, fixture.centerWiFiChannel().channel)
        assertEquals(level, fixture.level)
        assertEquals(WiFiBand.GHZ2, fixture.wiFiBand)
        assertEquals(WiFiWidth.MHZ_80, fixture.wiFiWidth)
    }

    @Test
    fun testCenterFrequency() {
        assertEquals(centerFrequency, fixture.centerFrequency)
        assertEquals(centerFrequency - WiFiWidth.MHZ_40.frequencyWidthHalf, fixture.frequencyStart())
        assertEquals(centerFrequency + WiFiWidth.MHZ_40.frequencyWidthHalf, fixture.frequencyEnd())
    }

    @Test
    fun testInRange() {
        assertTrue(fixture.inRange(centerFrequency))
        assertTrue(fixture.inRange(centerFrequency - WiFiWidth.MHZ_40.frequencyWidthHalf))
        assertTrue(fixture.inRange(centerFrequency + WiFiWidth.MHZ_40.frequencyWidthHalf))
        assertFalse(fixture.inRange(centerFrequency - WiFiWidth.MHZ_40.frequencyWidthHalf - 1))
        assertFalse(fixture.inRange(centerFrequency + WiFiWidth.MHZ_40.frequencyWidthHalf + 1))
    }

    @Test
    fun testPrimaryWiFiChannel() {
        assertEquals(primaryChannel, fixture.primaryWiFiChannel().channel)
    }

    @Test
    fun testCenterWiFiChannel() {
        assertEquals(centerChannel, fixture.centerWiFiChannel().channel)
    }

    @Test
    fun testStrength() {
        assertEquals(Strength.THREE, fixture.strength())
    }

    @Test
    fun testDistance() {
        // setup
        val expected = String.format(Locale.ENGLISH, "~%.1fm", calculateDistance(primaryFrequency, level))
        // execute
        val actual: String = fixture.distance()
        // validate
        assertEquals(expected, actual)
    }

    @Test
    fun testEquals() {
        // setup
        val other = WiFiSignal(primaryFrequency, centerFrequency, WiFiWidth.MHZ_40, level, true)
        // execute & validate
        assertEquals(fixture, other)
        assertNotSame(fixture, other)
    }

    @Test
    fun testHashCode() {
        // setup
        val other = WiFiSignal(primaryFrequency, centerFrequency, WiFiWidth.MHZ_40, level, true)
        // execute & validate
        assertEquals(fixture.hashCode(), other.hashCode())
    }

    @Test
    fun testChannelDisplayWhenPrimaryAndCenterSame() {
        // setup
        val fixture = WiFiSignal(primaryFrequency, primaryFrequency, WiFiWidth.MHZ_40, level, true)
        // execute & validate
        assertEquals("5", fixture.channelDisplay())
    }

    @Test
    fun testChannelDisplayWhenPrimaryAndCenterDifferent() {
        // setup
        val fixture = WiFiSignal(primaryFrequency, centerFrequency, WiFiWidth.MHZ_40, level, true)
        // execute & validate
        assertEquals("5(6)", fixture.channelDisplay())
    }

}
