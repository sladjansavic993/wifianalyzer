//SS
package com.sladjan.wifianalyzer.wifi.channelgraph

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.*
import com.sladjan.util.EMPTY
import com.sladjan.wifianalyzer.RobolectricUtil
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.band.WiFiWidth
import com.sladjan.wifianalyzer.wifi.graphutils.GraphDataPoint
import com.sladjan.wifianalyzer.wifi.graphutils.GraphViewWrapper
import com.sladjan.wifianalyzer.wifi.graphutils.MAX_Y
import com.sladjan.wifianalyzer.wifi.model.WiFiAdditional
import com.sladjan.wifianalyzer.wifi.model.WiFiDetail
import com.sladjan.wifianalyzer.wifi.model.WiFiIdentifier
import com.sladjan.wifianalyzer.wifi.model.WiFiSignal
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class DataManagerTest {
    private val level = -40
    private val mainActivity = RobolectricUtil.INSTANCE.activity
    private val fixture = DataManager()

    @Test
    fun testNewSeries() {
        // setup
        val wiFiChannelPair = WiFiBand.GHZ2.wiFiChannels.wiFiChannelPairs()[0]
        val expected = makeWiFiDetails(wiFiChannelPair.first.frequency)
        // execute
        val actual = fixture.newSeries(expected, wiFiChannelPair)
        // validate
        assertEquals(expected.size - 1, actual.size)
        assertTrue(actual.contains(expected[0]))
        assertFalse(actual.contains(expected[1]))
        assertTrue(actual.contains(expected[2]))
    }

    @Test
    fun testGraphDataPoints() {
        // setup
        val expected = makeWiFiDetail()
        // execute
        val actual = fixture.graphDataPoints(expected, MAX_Y)
        // validate
        assertEquals(5, actual.size)
        assertEquals(GraphDataPoint(2445, -100).toString(), actual[0].toString())
        assertEquals(GraphDataPoint(2450, level).toString(), actual[1].toString())
        assertEquals(GraphDataPoint(2455, level).toString(), actual[2].toString())
        assertEquals(GraphDataPoint(2460, level).toString(), actual[3].toString())
        assertEquals(GraphDataPoint(2465, -100).toString(), actual[4].toString())
    }

    @Test
    fun testGraphDataPointsExpectLevelToEqualToLevelMax() {
        // setup
        val expectedLevel = level - 10
        val expected = makeWiFiDetail()
        // execute
        val actual = fixture.graphDataPoints(expected, expectedLevel)
        // validate
        assertEquals(5, actual.size)
        assertEquals(GraphDataPoint(2445, -100).toString(), actual[0].toString())
        assertEquals(GraphDataPoint(2450, expectedLevel).toString(), actual[1].toString())
        assertEquals(GraphDataPoint(2455, expectedLevel).toString(), actual[2].toString())
        assertEquals(GraphDataPoint(2460, expectedLevel).toString(), actual[3].toString())
        assertEquals(GraphDataPoint(2465, -100).toString(), actual[4].toString())
    }

    @Test
    fun testAddSeriesDataWithExistingWiFiDetails() {
        // setup
        val graphViewWrapper: GraphViewWrapper = mock()
        val wiFiDetail = makeWiFiDetail()
        val wiFiDetails = setOf(wiFiDetail)
        val dataPoints = fixture.graphDataPoints(wiFiDetail, MAX_Y)
        whenever(graphViewWrapper.newSeries(wiFiDetail)).thenReturn(false)
        // execute
        fixture.addSeriesData(graphViewWrapper, wiFiDetails, MAX_Y)
        // validate
        verify(graphViewWrapper).newSeries(wiFiDetail)
        verify(graphViewWrapper).updateSeries(wiFiDetail, dataPoints, true)
    }

    @Test
    fun testAddSeriesDataNewWiFiDetails() {
        // setup
        val graphViewWrapper: GraphViewWrapper = mock()
        val wiFiDetail = makeWiFiDetail()
        val wiFiDetails = setOf(wiFiDetail)
        whenever(graphViewWrapper.newSeries(wiFiDetail)).thenReturn(true)
        // execute
        fixture.addSeriesData(graphViewWrapper, wiFiDetails, MAX_Y)
        // validate
        verify(graphViewWrapper).newSeries(wiFiDetail)
        verify(graphViewWrapper).addSeries(eq(wiFiDetail), any(), eq(true))
    }

    @Test
    fun testInRangeWithValidFrequency() {
        // setup
        val wiFiChannelPair = WiFiBand.GHZ2.wiFiChannels.wiFiChannelPairs()[0]
        // execute & validate
        assertTrue(wiFiChannelPair.inRange(makeWiFiDetail(frequency = wiFiChannelPair.first.frequency)))
        assertTrue(wiFiChannelPair.inRange(makeWiFiDetail(frequency = wiFiChannelPair.second.frequency)))
        assertTrue(wiFiChannelPair.inRange(makeWiFiDetail(frequency = (wiFiChannelPair.first.frequency + wiFiChannelPair.second.frequency) / 2)))
    }

    @Test
    fun testInRangeWithInvalidValidFrequency() {
        // setup
        val wiFiChannelPair = WiFiBand.GHZ2.wiFiChannels.wiFiChannelPairs()[0]
        // execute & validate
        assertFalse(wiFiChannelPair.inRange(makeWiFiDetail(frequency = wiFiChannelPair.first.frequency - 1)))
        assertFalse(wiFiChannelPair.inRange(makeWiFiDetail(frequency = wiFiChannelPair.second.frequency + 1)))
    }

    private fun makeWiFiDetail(SSID: String = "SSID", frequency: Int = 2455): WiFiDetail {
        val wiFiSignal = WiFiSignal(frequency, frequency, WiFiWidth.MHZ_20, level, true)
        val wiFiIdentifier = WiFiIdentifier(SSID, "BSSID")
        return WiFiDetail(wiFiIdentifier, String.EMPTY, wiFiSignal, WiFiAdditional.EMPTY)
    }

    private fun makeWiFiDetails(frequency: Int): List<WiFiDetail> {
        return listOf(makeWiFiDetail("SSID1", frequency), makeWiFiDetail("SSID2", -frequency), makeWiFiDetail("SSID3", frequency))
    }

}