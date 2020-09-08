//SS
package com.sladjan.wifianalyzer

import android.content.SharedPreferences
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.navigation.NavigationView
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sladjan.util.EMPTY
import com.sladjan.wifianalyzer.navigation.NavigationMenu
import com.sladjan.wifianalyzer.navigation.NavigationMenuController
import com.sladjan.wifianalyzer.navigation.options.OptionMenu
import com.sladjan.wifianalyzer.permission.PermissionService
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class MainActivityTest {
    private val sharedPreferences: SharedPreferences = mock()
    private val fixture = Robolectric.buildActivity(MainActivity::class.java).create().resume().get()

    @After
    fun tearDown() {
        MainContextHelper.INSTANCE.restore()
    }

    @Test
    fun testMainActivity() {
        assertTrue(MainContext.INSTANCE.scannerService.running())
    }

    @Test
    fun testOnPauseWillPauseScanner() {
        // setup
        val scannerService = MainContextHelper.INSTANCE.scannerService
        // execute
        fixture.onPause()
        // validate
        verify(scannerService).pause()
    }

    @Test
    fun testOnResumeCallsOptionMenuResume() {
        // setup
        val permissionService: PermissionService = mock()
        fixture.permissionService = permissionService
        val scannerService = MainContextHelper.INSTANCE.scannerService
        whenever(permissionService.permissionGranted()).thenReturn(true)
        // execute
        fixture.onResume()
        // validate
        verify(permissionService).permissionGranted()
        verify(scannerService).resume()
    }

    @Test
    fun testOnCreateOptionsMenu() {
        // setup
        val menu: Menu = mock()
        val optionMenu: OptionMenu = mock()
        fixture.optionMenu = optionMenu
        // execute
        val actual = fixture.onCreateOptionsMenu(menu)
        // validate
        assertTrue(actual)
        verify(optionMenu).create(fixture, menu)
    }

    @Test
    fun testOnOptionsItemSelected() {
        // setup
        val menuItem: MenuItem = mock()
        val optionMenu: OptionMenu = mock()
        fixture.optionMenu = optionMenu
        // execute
        val actual = fixture.onOptionsItemSelected(menuItem)
        // validate
        assertTrue(actual)
        verify(optionMenu).select(menuItem)
    }

    @Test
    fun testOnConfigurationChanged() {
        // setup
        val configuration = fixture.resources.configuration
        val drawerNavigation: DrawerNavigation = mock()
        fixture.drawerNavigation = drawerNavigation
        // execute
        fixture.onConfigurationChanged(configuration)
        // validate
        verify(drawerNavigation).onConfigurationChanged(configuration)
    }

    @Test
    fun testOnPostCreate() {
        // setup
        val drawerNavigation: DrawerNavigation = mock()
        fixture.drawerNavigation = drawerNavigation
        // execute
        fixture.onPostCreate(null)
        // validate
        verify(drawerNavigation).syncState()
    }

    @Test
    fun testOnStop() {
        // setup
        val scanner = MainContextHelper.INSTANCE.scannerService
        // execute
        fixture.onStop()
        // validate
        verify(scanner).stop()
    }

    @Test
    fun testUpdateShouldUpdateScanner() {
        // setup
        val scanner = MainContextHelper.INSTANCE.scannerService
        // execute
        fixture.update()
        // validate
        verify(scanner).update()
    }

    @Test
    fun testOnSharedPreferenceChangedShouldUpdateScanner() {
        // setup
        val scanner = MainContextHelper.INSTANCE.scannerService
        // execute
        fixture.onSharedPreferenceChanged(sharedPreferences, String.EMPTY)
        // validate
        verify(scanner).update()
    }

    @Test
    fun testOptionMenu() {
        // execute
        val actual = fixture.optionMenu
        // validate
        assertNotNull(actual)
    }

    @Test
    fun testGetCurrentMenuItem() {
        // setup
        val menuItem: MenuItem = mock()
        val navigationMenuController: NavigationMenuController = mock()
        whenever(navigationMenuController.currentMenuItem()).thenReturn(menuItem)
        fixture.navigationMenuController = navigationMenuController
        // execute
        val actual = fixture.currentMenuItem()
        // validate
        assertEquals(menuItem, actual)
        verify(navigationMenuController).currentMenuItem()
    }

    @Test
    fun testGetCurrentNavigationMenu() {
        // setup
        val navigationMenu = NavigationMenu.CHANNEL_GRAPH
        val navigationMenuController: NavigationMenuController = mock()
        whenever(navigationMenuController.currentNavigationMenu()).thenReturn(navigationMenu)
        fixture.navigationMenuController = navigationMenuController
        // execute
        val actual = fixture.currentNavigationMenu()
        // validate
        assertEquals(navigationMenu, actual)
        verify(navigationMenuController).currentNavigationMenu()
    }

    @Test
    fun testSetCurrentNavigationMenu() {
        // setup
        val navigationMenu = NavigationMenu.CHANNEL_GRAPH
        val settings = MainContextHelper.INSTANCE.settings
        val navigationMenuController: NavigationMenuController = mock()
        fixture.navigationMenuController = navigationMenuController
        // execute
        fixture.currentNavigationMenu(navigationMenu)
        // validate
        verify(navigationMenuController).currentNavigationMenu(navigationMenu)
        verify(settings).saveSelectedMenu(navigationMenu)
    }

    @Test
    fun testGetNavigationView() {
        // setup
        val navigationMenuController: NavigationMenuController = mock()
        val navigationView: NavigationView = mock()
        whenever(navigationMenuController.navigationView).thenReturn(navigationView)
        fixture.navigationMenuController = navigationMenuController
        // execute
        val actual = fixture.navigationView()
        // validate
        assertEquals(navigationView, actual)
        verify(navigationMenuController).navigationView
    }
}