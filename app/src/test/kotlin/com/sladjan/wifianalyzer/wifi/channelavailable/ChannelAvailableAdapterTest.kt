//SS
package com.sladjan.wifianalyzer.wifi.channelavailable

import android.os.Build
import android.view.ViewGroup
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sladjan.wifianalyzer.MainContextHelper.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.RobolectricUtil
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.band.WiFiChannelCountry.Companion.find
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.util.*

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class ChannelAvailableAdapterTest {
    private val mainActivity = RobolectricUtil.INSTANCE.activity
    private val settings = INSTANCE.settings
    private val currentLocale = Locale.getDefault()
    private val wiFiChannelCountry = find(currentLocale.country)
    private val fixture = ChannelAvailableAdapter(mainActivity, listOf(wiFiChannelCountry))

    @Before
    fun setUp() {
        whenever(settings.languageLocale()).thenReturn(currentLocale)
    }

    @After
    fun tearDown() {
        verify(settings, atLeastOnce()).languageLocale()
        INSTANCE.restore()
    }

    @Test
    fun testGetView() {
        // setup
        val resources = mainActivity.resources
        val wiFiBand2 = resources.getString(WiFiBand.GHZ2.textResource)
        val wiFiBand5 = resources.getString(WiFiBand.GHZ5.textResource)
        val expected = "${wiFiChannelCountry.countryCode()} - ${wiFiChannelCountry.countryName(currentLocale)}"
        val expectedGHZ2 = wiFiChannelCountry.channelsGHZ2().joinToString(",")
        val expectedGHZ5 = wiFiChannelCountry.channelsGHZ5().joinToString(",")
        val viewGroup = mainActivity.findViewById<ViewGroup>(android.R.id.content)
        // execute
        val actual = fixture.getView(0, null, viewGroup)
        // validate
        assertNotNull(actual)
        assertEquals(expected, actual.findViewById<TextView>(R.id.channel_available_country).text)
        assertEquals("$wiFiBand2 : ", actual.findViewById<TextView>(R.id.channel_available_title_ghz_2).text)
        assertEquals(expectedGHZ2, actual.findViewById<TextView>(R.id.channel_available_ghz_2).text)
        assertEquals("$wiFiBand5 : ", actual.findViewById<TextView>(R.id.channel_available_title_ghz_5).text)
        assertEquals(expectedGHZ5, actual.findViewById<TextView>(R.id.channel_available_ghz_5).text)
    }

}