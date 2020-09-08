//SS
package com.sladjan.wifianalyzer.wifi.channelgraph

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.sladjan.wifianalyzer.MainContextHelper
import com.sladjan.wifianalyzer.RobolectricUtil
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.model.WiFiConnection
import com.sladjan.wifianalyzer.wifi.model.WiFiData
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class ChannelGraphAdapterTest {
    private val mainActivity = RobolectricUtil.INSTANCE.activity
    private val channelGraphNavigation: ChannelGraphNavigation = mock()
    private val fixture = ChannelGraphAdapter(channelGraphNavigation)

    @After
    fun tearDown() {
        verifyNoMoreInteractions(channelGraphNavigation)
        MainContextHelper.INSTANCE.restore()
    }

    @Test
    fun testGetGraphViewNotifiers() {
        // setup
        val expected = WiFiBand.values().map { it.wiFiChannels.wiFiChannelPairs().size }.sum()
        // execute
        val graphViewNotifiers = fixture.graphViewNotifiers()
        // validate
        Assert.assertEquals(expected, graphViewNotifiers.size)
    }

    @Test
    fun testGetGraphViews() {
        // setup
        val expected = WiFiBand.values().map { it.wiFiChannels.wiFiChannelPairs().size }.sum()
        // execute
        val graphViews = fixture.graphViews()
        // validate
        Assert.assertEquals(expected, graphViews.size)
    }

    @Test
    fun testUpdate() {
        // setup
        val wiFiData = WiFiData(listOf(), WiFiConnection.EMPTY)
        // execute
        fixture.update(wiFiData)
        // validate
        verify(channelGraphNavigation).update(wiFiData)
    }
}