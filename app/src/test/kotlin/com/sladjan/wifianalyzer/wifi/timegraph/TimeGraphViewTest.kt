
package com.sladjan.wifianalyzer.wifi.timegraph

import android.os.Build
import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jjoe64.graphview.GraphView
import com.nhaarman.mockitokotlin2.*
import com.sladjan.wifianalyzer.MainContext
import com.sladjan.wifianalyzer.MainContextHelper
import com.sladjan.wifianalyzer.RobolectricUtil
import com.sladjan.wifianalyzer.settings.ThemeStyle
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
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
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class TimeGraphViewTest {
    private val dataManager: DataManager = mock()
    private val graphViewWrapper: GraphViewWrapper = mock()
    private val fixture: TimeGraphView = spy(TimeGraphView(WiFiBand.GHZ2, dataManager, graphViewWrapper))

    @After
    fun tearDown() {
        verifyNoMoreInteractions(dataManager)
        verifyNoMoreInteractions(graphViewWrapper)
        MainContextHelper.INSTANCE.restore()
    }

    @Test
    fun testUpdate() {
        // setup
        val settings = MainContextHelper.INSTANCE.settings
        val wiFiDetails: List<WiFiDetail> = listOf()
        val newSeries: Set<WiFiDetail> = setOf()
        val wiFiData = WiFiData(wiFiDetails, WiFiConnection.EMPTY)
        val predicate: Predicate = TruePredicate()
        doReturn(predicate).whenever(fixture).predicate(settings)
        whenever(dataManager.addSeriesData(graphViewWrapper, wiFiDetails, MAX_Y)).thenReturn(newSeries)
        whenever(settings.sortBy()).thenReturn(SortBy.SSID)
        whenever(settings.timeGraphLegend()).thenReturn(GraphLegend.LEFT)
        whenever(settings.wiFiBand()).thenReturn(WiFiBand.GHZ2)
        whenever(settings.graphMaximumY()).thenReturn(MAX_Y)
        whenever(settings.themeStyle()).thenReturn(ThemeStyle.DARK)
        // execute
        fixture.update(wiFiData)
        // validate
        verify(fixture).predicate(settings)
        verify(dataManager).addSeriesData(graphViewWrapper, wiFiDetails, MAX_Y)
        verify(graphViewWrapper).removeSeries(newSeries)
        verify(graphViewWrapper).updateLegend(GraphLegend.LEFT)
        verify(graphViewWrapper).visibility(View.VISIBLE)
        verify(settings).sortBy()
        verify(settings).timeGraphLegend()
        verify(settings).graphMaximumY()
        verify(settings).wiFiBand()
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
        Assert.assertEquals(expected, actual)
        verify(graphViewWrapper).graphView
        verifyNoMoreInteractions(expected)
    }

    @Test
    fun testMakeGraphView() {
        // setup
        RobolectricUtil.INSTANCE.activity
        // execute
        val actual = makeGraphView(MainContext.INSTANCE, 10, ThemeStyle.DARK)
        // validate
        Assert.assertNotNull(actual)
    }

    @Test
    fun testMakeGraphViewWrapper() {
        // setup
        RobolectricUtil.INSTANCE.activity
        // execute
        val actual = makeGraphViewWrapper()
        // validate
        Assert.assertNotNull(actual)
    }
}