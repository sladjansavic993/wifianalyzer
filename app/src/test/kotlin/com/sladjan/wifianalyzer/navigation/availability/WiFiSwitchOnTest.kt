//SS
package com.sladjan.wifianalyzer.navigation.availability

import android.os.Build
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.*
import com.sladjan.util.fromHtml
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.RobolectricUtil
import com.sladjan.wifianalyzer.navigation.options.OptionMenu
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class WiFiSwitchOnTest {
    private val spacer = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
    private val mainActivity = RobolectricUtil.INSTANCE.activity
    private val optionMenu: OptionMenu = mock()
    private val menu: Menu = mock()
    private val menuItem: MenuItem = mock()
    private val actionBar: ActionBar = mock()

    @After
    fun tearDown() {
        verifyNoMoreInteractions(optionMenu)
        verifyNoMoreInteractions(menu)
        verifyNoMoreInteractions(menuItem)
        verifyNoMoreInteractions(actionBar)
    }

    @Test
    fun testNavigationOptionWiFiSwitchOnSetSubtitle() {
        // setup
        val expected = withExpectedSubtitle()
        // execute
        navigationOptionWiFiSwitchOn(mainActivity)
        // validate
        val actual = mainActivity.supportActionBar!!.subtitle
        assertTrue(expected.isNotEmpty())
        assertEquals(expected.length, actual!!.length)
        for (i in expected.indices) {
            assertEquals("" + i + ":" + expected[i] + ":" + actual[i], expected[i], actual[i])
        }
    }

    @Test
    fun testNavigationOptionWiFiSwitchOnWithNoActionBarDoesNotSetSubtitle() {
        // setup
        val mainActivity: MainActivity = mock()
        whenever(mainActivity.supportActionBar).thenReturn(null)
        whenever(mainActivity.optionMenu).thenReturn(optionMenu)
        whenever(optionMenu.menu).thenReturn(null)
        // execute
        navigationOptionWiFiSwitchOn(mainActivity)
        // validate
        verify(mainActivity).supportActionBar
        verify(mainActivity).optionMenu
        verify(optionMenu).menu
        verify(actionBar, never()).subtitle = any()
    }

    @Test
    fun testMakeSubtitleGHZ2() {
        // setup
        val color1 = 10
        val color2 = 20
        val text1 = "text1"
        val text2 = "text2"
        val expected = ("<font color='" + color1 + "'><strong>" + text1
                + "</strong></font>" + spacer
                + "<font color='" + color2 + "'><small>" + text2 + "</small></font>")
        // execute
        val actual = makeSubtitle(true, text1, text2, color1, color2)
        // validate
        assertEquals(expected, actual)
    }

    @Test
    fun testMakeSubtitleGHZ5() {
        // setup
        val color1 = 10
        val color2 = 20
        val text1 = "text1"
        val text2 = "text2"
        val expected = ("<font color='" + color2 + "'><small>" + text1
                + "</small></font>" + spacer
                + "<font color='" + color1 + "'><strong>" + text2 + "</strong></font>")
        // execute
        val actual = makeSubtitle(false, text1, text2, color1, color2)
        // validate
        assertEquals(expected, actual)
    }

    @Test
    fun testNavigationOptionWiFiSwitchOnWithWiFiBandVisible() {
        // execute
        navigationOptionWiFiSwitchOn(mainActivity)
        // validate
        mainActivity.optionMenu.menu?.findItem(R.id.action_wifi_band)?.isVisible?.let { assertTrue(it) }
    }

    @Test
    fun testNavigationOptionWiFiSwitchOnWithNoMenuDoesNotSetWiFiBandVisible() {
        // setup
        val mainActivity: MainActivity = mock()
        whenever(mainActivity.optionMenu).thenReturn(optionMenu)
        whenever(optionMenu.menu).thenReturn(null)
        // execute
        navigationOptionWiFiSwitchOn(mainActivity)
        // validate
        verify(mainActivity).optionMenu
        verify(optionMenu).menu
        verifyMenu()
    }

    private fun verifyMenu() {
        verify(menu, never()).findItem(R.id.action_wifi_band)
        verify(menuItem, never()).isVisible = any()
    }

    private fun withExpectedSubtitle(): CharSequence {
        val wiFiBand = INSTANCE.settings.wiFiBand()
        val resources = mainActivity.resources
        val wiFiBand2 = resources.getString(WiFiBand.GHZ2.textResource)
        val wiFiBand5 = resources.getString(WiFiBand.GHZ5.textResource)
        val colorSelected = mainActivity.getColor(R.color.selected)
        val colorNotSelected = mainActivity.getColor(R.color.regular)
        val subtitle = makeSubtitle(WiFiBand.GHZ2 == wiFiBand, wiFiBand2, wiFiBand5, colorSelected, colorNotSelected)
        return subtitle.fromHtml()
    }

}