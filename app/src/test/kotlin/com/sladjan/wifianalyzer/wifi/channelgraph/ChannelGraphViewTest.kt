//SS
package com.sladjan.wifianalyzer.wifi.channelgraph

import android.os.Build
import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jjoe64.graphview.GraphView
import com.nhaarman.mockitokotlin2.*
import com.sladjan.wifianalyzer.Configuration
import com.sladjan.wifianalyzer.MainContext
import com.sladjan.wifianalyzer.MainContextHelper
import com.sladjan.wifianalyzer.RobolectricUtil
import com.sladjan.wifianalyzer.settings.Settings
import com.sladjan.wifianalyzer.settings.ThemeStyle
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.band.WiFiChannel
import com.sladjan.wifianalyzer.wifi.band.WiFiChannelPair
import com.sladjan.wifianalyzer.wifi.graphutils.GraphLegend
import com.sladjan.wifianalyzer.wifi.graphutils.GraphViewWrapper
import com.sladjan.wifianalyzer.wifi.graphutils.MAX_Y
import com.sladjan.wifianalyzer.wifi.model.SortBy
import com.sladjan.wifianalyzer.wifi.model.WiFiConnection
import com.sladjan.wifianalyzer.wifi.model.WiFiData
import com.sladjan.wifianalyzer.wifi.model.WiFiDetail
import com.sladjan.wifianalyzer.wifi.predicate.Predicate
import com.sladjan.wifianalyzer.wifi.predicate.TruePredicate
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class ChannelGraphViewTest {
    private val wiFiChannelPair: WiFiChannelPair = WiFiChannelPair(WiFiChannel.UNKNOWN, WiFiChannel.UNKNOWN)
    private val settings: Settings = MainContextHelper.INSTANCE.settings
    private val configuration: Configuration = MainContextHelper.INSTANCE.configuration
    private val graphViewWrapper: GraphViewWrapper = mock()
    private val dataManager: DataManager = mock()
    private val fixture: ChannelGraphView = spy(ChannelGraphView(WiFiBand.GHZ2, wiFiChannelPair, dataManager, graphViewWrapper))

    @After
    fun tearDown() {
        verifyNoMoreInteractions(graphViewWrapper)
        verifyNoMoreInteractions(dataManager)
        MainContextHelper.INSTANCE.restore()
    }

    @Test
    fun testUpdate() {
        // setup
        val newSeries: Set<WiFiDetail> = setOf()
        val wiFiDetails: List<WiFiDetail> = listOf()
        val wiFiData = WiFiData(wiFiDetails, WiFiConnection.EMPTY)
        val predicate: Predicate = TruePredicate()
        doReturn(predicate).whenever(fixture).predicate(settings)
        doReturn(true).whenever(fixture).selected()
        whenever(dataManager.newSeries(wiFiDetails, wiFiChannelPair)).thenReturn(newSeries)
        whenever(settings.channelGraphLegend()).thenReturn(GraphLegend.RIGHT)
        whenever(settings.graphMaximumY()).thenReturn(MAX_Y)
        whenever(settings.themeStyle()).thenReturn(ThemeStyle.DARK)
        whenever(settings.sortBy()).thenReturn(SortBy.CHANNEL)
        // execute
        fixture.update(wiFiData)
        // validate
        verify(fixture).selected()
        verify(fixture).predicate(settings)
        verify(dataManager).newSeries(wiFiDetails, wiFiChannelPair)
        verify(dataManager).addSeriesData(graphViewWrapper, newSeries, MAX_Y)
        verify(graphViewWrapper).removeSeries(newSeries)
        verify(graphViewWrapper).updateLegend(GraphLegend.RIGHT)
        verify(graphViewWrapper).visibility(View.VISIBLE)
        verify(settings).sortBy()
        verify(settings).channelGraphLegend()
        verify(settings).graphMaximumY()
        verifyNoMoreInteractions(settings)
    }

    @Test
    fun testGraphView() {
        // setup
        val expected: GraphView = mock()
        whenever(graphViewWrapper.graphView).thenReturn(expected)
        // execute
        val actual = fixture.graphView()
        // validate
        assertEquals(expected, actual)
        verify(graphViewWrapper).graphView
    }

    @Test
    fun testWiFiChannelPairNumX() {
        // setup
        val expected = 15
        val wiFiChannelPair: WiFiChannelPair = withWiFiChannelPair()
        // execute
        val actual = wiFiChannelPair.numX()
        // validate
        assertEquals(expected, actual)
    }

    @Test
    fun testWiFiChannelPairSelected() {
        // setup
        val fixture = withWiFiChannelPair()
        whenever(settings.wiFiBand()).thenReturn(WiFiBand.GHZ2)
        whenever(configuration.wiFiChannelPair).thenReturn(fixture)
        // execute
        val actual = fixture.selected(WiFiBand.GHZ2)
        // validate
        assertTrue(actual)
        verify(settings).wiFiBand()
        verify(configuration).wiFiChannelPair
    }

    @Test
    fun testWiFiChannelPairSelectedWithCurrentWiFiBandGHZ5() {
        // setup
        val fixture = withWiFiChannelPair()
        whenever(settings.wiFiBand()).thenReturn(WiFiBand.GHZ5)
        whenever(configuration.wiFiChannelPair).thenReturn(fixture)
        // execute
        val actual = fixture.selected(WiFiBand.GHZ2)
        // validate
        assertFalse(actual)
        verify(settings).wiFiBand()
        verify(configuration).wiFiChannelPair
    }

    @Test
    fun testWiFiChannelPairSelectedWithGHZ5() {
        // setup
        val fixture = withWiFiChannelPair()
        whenever(settings.wiFiBand()).thenReturn(WiFiBand.GHZ2)
        whenever(configuration.wiFiChannelPair).thenReturn(fixture)
        // execute
        val actual = fixture.selected(WiFiBand.GHZ5)
        // validate
        assertFalse(actual)
        verify(settings).wiFiBand()
        verify(configuration).wiFiChannelPair
    }

    @Test
    fun testWiFiChannelPairSelectedWithCurrentGHZ5() {
        // setup
        val fixture = withWiFiChannelPair()
        val wiFiChannelPair = withWiFiChannelPair(30)
        whenever(settings.wiFiBand()).thenReturn(WiFiBand.GHZ5)
        whenever(configuration.wiFiChannelPair).thenReturn(wiFiChannelPair)
        // execute
        val actual = fixture.selected(WiFiBand.GHZ5)
        // validate
        assertFalse(actual)
        verify(settings).wiFiBand()
        verify(configuration).wiFiChannelPair
    }

    @Test
    fun testMakeGraphView() {
        // setup
        RobolectricUtil.INSTANCE.activity
        val wiFiChannelPair = withWiFiChannelPair()
        // execute
        val actual = makeGraphView(MainContext.INSTANCE, 10, ThemeStyle.DARK, WiFiBand.GHZ2, wiFiChannelPair)
        // validate
        assertNotNull(actual)
    }

    @Test
    fun testMakeGraphViewWrapper() {
        // setup
        MainContextHelper.INSTANCE.restore()
        RobolectricUtil.INSTANCE.activity
        val wiFiChannelPair = withWiFiChannelPair()
        // execute
        val actual = makeGraphViewWrapper(WiFiBand.GHZ2, wiFiChannelPair)
        // validate
        assertNotNull(actual)
    }

    @Test
    fun testMakeDefaultSeries() {
        // setup
        val frequencyEnd = 10
        val minX = 20
        RobolectricUtil.INSTANCE.activity
        // execute
        val actual = makeDefaultSeries(frequencyEnd, minX)
        // validate
        assertNotNull(actual)
    }

    private fun withWiFiChannelPair(channel: Int = 10): WiFiChannelPair =
            WiFiChannelPair(WiFiChannel(channel, 100), WiFiChannel(20, 200))

}