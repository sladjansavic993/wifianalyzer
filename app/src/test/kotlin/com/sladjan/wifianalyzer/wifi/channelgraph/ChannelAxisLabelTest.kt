//SS
package com.sladjan.wifianalyzer.wifi.channelgraph

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.sladjan.util.EMPTY
import com.sladjan.wifianalyzer.MainContextHelper
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.band.WiFiChannels
import com.sladjan.wifianalyzer.wifi.graphutils.MAX_Y
import com.sladjan.wifianalyzer.wifi.graphutils.MIN_Y
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class ChannelAxisLabelTest {
    private val settings = MainContextHelper.INSTANCE.settings
    private val fixture = ChannelAxisLabel(WiFiBand.GHZ2, WiFiBand.GHZ2.wiFiChannels.wiFiChannelPairs()[0])

    @After
    fun tearDown() {
        verifyNoMoreInteractions(settings)
        MainContextHelper.INSTANCE.restore()
    }

    @Test
    fun testYAxis() {
        // execute & verify
        assertEquals(String.EMPTY, fixture.formatLabel(MIN_Y.toDouble(), false))
        assertEquals("-99", fixture.formatLabel(MIN_Y + 1.toDouble(), false))
        assertEquals("0", fixture.formatLabel(MAX_Y.toDouble(), false))
        assertEquals(String.EMPTY, fixture.formatLabel(MAX_Y + 1.toDouble(), false))
    }

    @Test
    fun testXAxis() {
        // setup
        val (channel, frequency) = WiFiBand.GHZ2.wiFiChannels.wiFiChannelFirst()
        whenever(settings.countryCode()).thenReturn(Locale.US.country)
        // execute
        val actual = fixture.formatLabel(frequency.toDouble(), true)
        // validate
        assertEquals("" + channel, actual)
        verify(settings).countryCode()
    }

    @Test
    fun testXAxisWithFrequencyInRange() {
        // setup
        val (channel, frequency) = WiFiBand.GHZ2.wiFiChannels.wiFiChannelFirst()
        whenever(settings.countryCode()).thenReturn(Locale.US.country)
        // execute & validate
        assertEquals("" + channel, fixture.formatLabel(frequency - 2.toDouble(), true))
        assertEquals("" + channel, fixture.formatLabel(frequency + 2.toDouble(), true))
        verify(settings, times(2)).countryCode()
    }

    @Test
    fun testXAxisWithFrequencyNotAllowedInLocale() {
        // setup
        val (_, frequency) = WiFiBand.GHZ2.wiFiChannels.wiFiChannelLast()
        // execute
        val actual = fixture.formatLabel(frequency.toDouble(), true)
        // validate
        assertEquals(String.EMPTY, actual)
    }

    @Test
    fun testXAxisWithUnknownFrequencyReturnEmptyString() {
        // setup
        val wiFiChannels = WiFiBand.GHZ2.wiFiChannels
        val (_, frequency) = wiFiChannels.wiFiChannelFirst()
        // execute
        val actual = fixture.formatLabel(frequency - WiFiChannels.FREQUENCY_OFFSET.toDouble(), true)
        // validate
        assertEquals(String.EMPTY, actual)
    }
}