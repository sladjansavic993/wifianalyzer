//SS
package com.sladjan.wifianalyzer.navigation.availability

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import com.nhaarman.mockitokotlin2.*
import com.sladjan.util.EMPTY
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.MainContextHelper.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.navigation.options.OptionMenu
import org.junit.After
import org.junit.Test

class WiFiSwitchOffTest {
    private val mainActivity: MainActivity = mock()
    private val optionMenu: OptionMenu = mock()
    private val menu: Menu = mock()
    private val menuItem: MenuItem = mock()
    private val actionBar: ActionBar = mock()

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mainActivity)
        verifyNoMoreInteractions(optionMenu)
        verifyNoMoreInteractions(menu)
        verifyNoMoreInteractions(menuItem)
        verifyNoMoreInteractions(actionBar)
        INSTANCE.restore()
    }

    @Test
    fun testNavigationOptionWiFiSwitchOffWithActionBarSetEmptySubtitle() {
        // setup
        whenever(mainActivity.supportActionBar).thenReturn(actionBar)
        whenever(mainActivity.optionMenu).thenReturn(optionMenu)
        whenever(optionMenu.menu).thenReturn(null)
        // execute
        navigationOptionWiFiSwitchOff(mainActivity)
        // validate
        verify(mainActivity).supportActionBar
        verify(actionBar).subtitle = String.EMPTY
        verify(mainActivity).optionMenu
        verify(optionMenu).menu
    }

    @Test
    fun testNavigationOptionWiFiSwitchOffWithNoActionBarDoesNotSetSubtitle() {
        // setup
        whenever(mainActivity.supportActionBar).thenReturn(null)
        whenever(mainActivity.optionMenu).thenReturn(optionMenu)
        whenever(optionMenu.menu).thenReturn(null)
        // execute
        navigationOptionWiFiSwitchOff(mainActivity)
        // validate
        verify(mainActivity).supportActionBar
        verify(actionBar, never()).subtitle = any()
        verify(mainActivity).optionMenu
        verify(optionMenu).menu
    }

    @Test
    fun testNavigationOptionWiFiSwitchOffWithOptionMenuSetVisibleFalse() {
        // setup
        whenever(mainActivity.supportActionBar).thenReturn(null)
        whenever(mainActivity.optionMenu).thenReturn(optionMenu)
        whenever(optionMenu.menu).thenReturn(menu)
        whenever(menu.findItem(R.id.action_wifi_band)).thenReturn(menuItem)
        // execute
        navigationOptionWiFiSwitchOff(mainActivity)
        // validate
        verify(mainActivity).supportActionBar
        verify(mainActivity).optionMenu
        verify(optionMenu).menu
        verify(menu).findItem(R.id.action_wifi_band)
        verify(menuItem).isVisible = false
    }

    @Test
    fun testNavigationOptionWiFiSwitchOffWithNoOptionMenuDoesNotSetWiFiBandVisible() {
        // setup
        whenever(mainActivity.supportActionBar).thenReturn(null)
        whenever(mainActivity.optionMenu).thenReturn(optionMenu)
        whenever(optionMenu.menu).thenReturn(null)
        // execute
        navigationOptionWiFiSwitchOff(mainActivity)
        // validate
        verify(mainActivity).optionMenu
        verify(optionMenu).menu
        verify(menu, never()).findItem(R.id.action_wifi_band)
        verify(menuItem, never()).isVisible = any()
        verify(mainActivity).supportActionBar
    }

    @Test
    fun testNavigationOptionWiFiSwitchOffWithNoMenuDoesNotSetWiFiBandVisible() {
        // setup
        whenever(mainActivity.supportActionBar).thenReturn(null)
        whenever(mainActivity.optionMenu).thenReturn(optionMenu)
        whenever(optionMenu.menu).thenReturn(null)
        // execute
        navigationOptionWiFiSwitchOff(mainActivity)
        // validate
        verify(mainActivity).optionMenu
        verify(optionMenu).menu
        verify(menu, never()).findItem(R.id.action_wifi_band)
        verify(menuItem, never()).isVisible = any()
        verify(mainActivity).supportActionBar
    }

}