//SS
package com.sladjan.wifianalyzer.wifi.channelgraph

import android.os.Build
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sladjan.wifianalyzer.MainContextHelper
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.RobolectricUtil
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.band.WiFiChannelPair
import com.sladjan.wifianalyzer.wifi.band.WiFiChannelsGHZ5
import com.sladjan.wifianalyzer.wifi.model.SortBy
import com.sladjan.wifianalyzer.wifi.model.WiFiData
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.util.*

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class ChannelGraphNavigationTest {
    private val mainActivity = RobolectricUtil.INSTANCE.activity
    private val scanner = MainContextHelper.INSTANCE.scannerService
    private val settings = MainContextHelper.INSTANCE.settings
    private val configuration = MainContextHelper.INSTANCE.configuration
    private val layout: View = mock()
    private val views: MutableMap<WiFiChannelPair, View> = mutableMapOf()
    private val fixture = ChannelGraphNavigation(layout, mainActivity)

    @Before
    fun setUp() {
        navigationSet.keys.forEach { setUpExecute(it) }
        fixture.initialize()
    }

    @After
    fun tearDown() {
        MainContextHelper.INSTANCE.restore()
    }

    @Test
    fun testMapping() {
        assertEquals(WiFiChannelsGHZ5.SETS.size, navigationSet.size)
        WiFiChannelsGHZ5.SETS.forEach { Assert.assertNotNull(navigationSet[it]) }
    }

    @Test
    fun testInitialize() {
        navigationSet.values.forEach { verify(layout).findViewById<View>(it) }
        views.values.forEach { verify(it).setOnClickListener(ArgumentMatchers.any(View.OnClickListener::class.java)) }
    }

    @Test
    fun testUpdateWithGHZ2() {
        // setup
        whenever(settings.countryCode()).thenReturn(Locale.US.country)
        whenever(settings.wiFiBand()).thenReturn(WiFiBand.GHZ2)
        // execute
        fixture.update(WiFiData.EMPTY)
        // validate
        verify(layout).visibility = View.GONE
        verify(settings).countryCode()
        verify(settings).wiFiBand()
    }

    @Test
    fun testUpdateWithGHZ5AndUS() {
        // setup
        val colorSelected = ContextCompat.getColor(mainActivity, R.color.selected)
        val colorNotSelected = ContextCompat.getColor(mainActivity, R.color.background)
        val selectedKey = WiFiBand.GHZ5.wiFiChannels.wiFiChannelPairs()[0]
        whenever(configuration.wiFiChannelPair).thenReturn(selectedKey)
        whenever(settings.countryCode()).thenReturn(Locale.US.country)
        whenever(settings.wiFiBand()).thenReturn(WiFiBand.GHZ5)
        whenever(settings.sortBy()).thenReturn(SortBy.CHANNEL)
        // execute
        fixture.update(WiFiData.EMPTY)
        // validate
        verify(layout).visibility = View.VISIBLE
        views.keys.forEach { pairUpdate(selectedKey, colorSelected, colorNotSelected, it) }
        navigationSet.values.forEach { verify(layout, times(2)).findViewById<View>(it) }
        verify(settings).countryCode()
        verify(settings, times(2)).wiFiBand()
        verify(settings).sortBy()
        verify(configuration).wiFiChannelPair
    }

    @Test
    fun testUpdateGHZ5WithJapan() {
        // setup
        whenever(settings.countryCode()).thenReturn(Locale.JAPAN.country)
        whenever(settings.wiFiBand()).thenReturn(WiFiBand.GHZ5)
        whenever(settings.sortBy()).thenReturn(SortBy.CHANNEL)
        // execute
        fixture.update(WiFiData.EMPTY)
        // validate
        verify(layout).visibility = View.VISIBLE
        views.entries.forEach { verify(it.value).visibility = if (WiFiChannelsGHZ5.SET3 == it.key) View.GONE else View.VISIBLE }
        verify(settings).countryCode()
        verify(settings, times(2)).wiFiBand()
        verify(settings).sortBy()
    }

    @Test
    fun testUpdateGHZ5WithCountryThatHasOnlyOneSet() {
        // setup
        whenever(settings.countryCode()).thenReturn("IL")
        whenever(settings.wiFiBand()).thenReturn(WiFiBand.GHZ5)
        // execute
        fixture.update(WiFiData.EMPTY)
        // validate
        verify(layout).visibility = View.GONE
        verify(settings).countryCode()
        verify(settings).wiFiBand()
    }

    @Test
    fun testSetOnClickListener() {
        // setup
        val expected = WiFiChannelsGHZ5.SET3
        // execute
        fixture.onClickListener(expected)
        // validate
        verify(configuration).wiFiChannelPair = expected
        verify(scanner).update()
    }

    private fun setUpExecute(key: WiFiChannelPair) {
        val id = navigationSet[key] as Int
        val button: Button = mock()
        views[key] = button
        whenever(layout.findViewById<View>(id)).thenReturn(button)
        whenever(button.text).thenReturn("ButtonName")
    }

    private fun pairUpdate(selectedKey: WiFiChannelPair, colorSelected: Int, colorNotSelected: Int, key: WiFiChannelPair) {
        val button = views[key] as Button
        verify(button).visibility = View.VISIBLE
        verify(button).setBackgroundColor(if (selectedKey == key) colorSelected else colorNotSelected)
        verify(button).isSelected = selectedKey == key
    }
}