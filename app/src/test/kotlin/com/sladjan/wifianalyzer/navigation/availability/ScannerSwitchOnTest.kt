//SS
package com.sladjan.wifianalyzer.navigation.availability

import android.view.Menu
import android.view.MenuItem
import com.nhaarman.mockitokotlin2.*
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.MainContextHelper.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.navigation.options.OptionMenu
import org.junit.After
import org.junit.Test

class ScannerSwitchOnTest {
    private val mainActivity: MainActivity = mock()
    private val optionMenu: OptionMenu = mock()
    private val menu: Menu = mock()
    private val menuItem: MenuItem = mock()
    private val scanner = INSTANCE.scannerService

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mainActivity)
        verifyNoMoreInteractions(optionMenu)
        verifyNoMoreInteractions(menu)
        verifyNoMoreInteractions(menuItem)
        verifyNoMoreInteractions(scanner)
        INSTANCE.restore()
    }

    @Test
    fun testNavigationOptionScannerSwitchOn() {
        // setup
        whenever(mainActivity.optionMenu).thenReturn(optionMenu)
        whenever(optionMenu.menu).thenReturn(menu)
        whenever(menu.findItem(R.id.action_scanner)).thenReturn(menuItem)
        // execute
        navigationOptionScannerSwitchOn(mainActivity)
        // validate
        verify(mainActivity).optionMenu
        verify(optionMenu).menu
        verify(menu).findItem(R.id.action_scanner)
        verify(scanner).running()
        verify(menuItem).isVisible = true
        verify(menuItem).setTitle(R.string.scanner_play)
        verify(menuItem).setIcon(R.drawable.ic_play_arrow)
    }

    @Test
    fun testNavigationOptionScannerSwitchOnWithScannerRunningUpdateMenuItemIconAndTitle() {
        // setup
        whenever(scanner.running()).thenReturn(true)
        whenever(mainActivity.optionMenu).thenReturn(optionMenu)
        whenever(optionMenu.menu).thenReturn(menu)
        whenever(menu.findItem(R.id.action_scanner)).thenReturn(menuItem)
        // execute
        navigationOptionScannerSwitchOn(mainActivity)
        // validate
        verify(mainActivity).optionMenu
        verify(optionMenu).menu
        verify(menu).findItem(R.id.action_scanner)
        verify(scanner).running()
        verify(menuItem).isVisible = true
        verify(menuItem).setTitle(R.string.scanner_pause)
        verify(menuItem).setIcon(R.drawable.ic_pause)
    }

    @Test
    fun testNavigationOptionScannerSwitchOnWithScannerNotRunningUpdateMenuItemIconAndTitle() {
        // setup
        whenever(scanner.running()).thenReturn(false)
        whenever(mainActivity.optionMenu).thenReturn(optionMenu)
        whenever(optionMenu.menu).thenReturn(menu)
        whenever(menu.findItem(R.id.action_scanner)).thenReturn(menuItem)
        // execute
        navigationOptionScannerSwitchOn(mainActivity)
        // validate
        verify(mainActivity).optionMenu
        verify(optionMenu).menu
        verify(menu).findItem(R.id.action_scanner)
        verify(scanner).running()
        verify(menuItem).isVisible = true
        verify(menuItem).setTitle(R.string.scanner_play)
        verify(menuItem).setIcon(R.drawable.ic_play_arrow)
    }

    @Test
    fun testNavigationOptionScannerSwitchOnWithNoMenuDoesNotSetVisibleTrue() {
        // setup
        whenever(mainActivity.optionMenu).thenReturn(optionMenu)
        whenever(optionMenu.menu).thenReturn(null)
        // execute
        navigationOptionScannerSwitchOn(mainActivity)
        // validate
        verify(mainActivity).optionMenu
        verify(optionMenu).menu
        verify(menu, never()).findItem(R.id.action_scanner)
        verify(menuItem, never()).isVisible = true
    }

}