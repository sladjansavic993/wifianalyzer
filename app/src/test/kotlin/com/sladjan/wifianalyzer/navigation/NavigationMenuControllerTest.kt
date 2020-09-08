//SS
package com.sladjan.wifianalyzer.navigation

import android.os.Build
import android.view.Menu
import android.view.MenuItem
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sladjan.wifianalyzer.RobolectricUtil
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class NavigationMenuControllerTest {
    private val mainActivity = RobolectricUtil.INSTANCE.activity
    private val fixture = mainActivity.navigationMenuController
    private val navigationView = fixture.navigationView
    private val bottomNavigationView = fixture.bottomNavigationView

    @After
    fun tearDown() {
        fixture.currentNavigationMenu(NavigationMenu.ACCESS_POINTS)
    }

    @Test
    fun testNavigationMenuView() {
        // execute
        val menu: Menu = navigationView.menu
        // validate
        assertEquals(NavigationMenu.values().size, menu.size())
        validateNavigationGroup(menu)
    }

    @Test
    fun testGetCurrentMenuItem() {
        // setup
        val expected = navigationViewMenuItem(NavigationMenu.ACCESS_POINTS)
        // execute
        val actual = fixture.currentMenuItem()
        // validate
        assertEquals(expected, actual)
        assertTrue(actual.isCheckable)
        assertTrue(actual.isChecked)
    }

    @Test
    fun testGetCurrentNavigationMenu() {
        // execute
        val actual = fixture.currentNavigationMenu()
        // validate
        assertEquals(NavigationMenu.ACCESS_POINTS, actual)
    }

    @Test
    fun testSetCurrentNavigationMenuWithNavigationView() {
        // setup
        val expected = NavigationMenu.CHANNEL_GRAPH
        // execute
        fixture.currentNavigationMenu(expected)
        // validate
        assertEquals(expected, fixture.currentNavigationMenu())
        assertTrue(navigationViewMenuItem(NavigationMenu.CHANNEL_GRAPH).isCheckable)
        assertTrue(navigationViewMenuItem(NavigationMenu.CHANNEL_GRAPH).isChecked)
        assertFalse(navigationViewMenuItem(NavigationMenu.ACCESS_POINTS).isCheckable)
        assertFalse(navigationViewMenuItem(NavigationMenu.ACCESS_POINTS).isChecked)
    }

    @Test
    fun testSetCurrentNavigationMenuWithBottomNavigationView() {
        // setup
        val expected = NavigationMenu.CHANNEL_GRAPH
        // execute
        fixture.currentNavigationMenu(expected)
        // validate
        assertEquals(expected, fixture.currentNavigationMenu())
        val menuItem = bottomNavigationMenuItem(NavigationMenu.CHANNEL_GRAPH)
        assertTrue(menuItem.isCheckable)
        assertTrue(menuItem.isChecked)
        assertFalse(bottomNavigationMenuItem(NavigationMenu.ACCESS_POINTS).isCheckable)
    }

    private fun navigationViewMenuItem(navigationMenu: NavigationMenu): MenuItem =
            navigationView.menu.findItem(navigationMenu.ordinal)

    private fun bottomNavigationMenuItem(navigationMenu: NavigationMenu): MenuItem =
            bottomNavigationView.menu.findItem(navigationMenu.ordinal)

    private fun validateNavigationGroup(menu: Menu): Unit =
            NavigationGroup.values().forEach { validateNavigationMenu(it, menu) }

    private fun validateNavigationMenu(navigationGroup: NavigationGroup, menu: Menu): Unit =
            navigationGroup.navigationMenus.forEach { validateMenuItem(menu, it, navigationGroup) }

    private fun validateMenuItem(menu: Menu, navigationMenu: NavigationMenu, navigationGroup: NavigationGroup) {
        val actual: MenuItem = menu.getItem(navigationMenu.ordinal)
        assertEquals(navigationGroup.ordinal, actual.groupId)
        assertEquals(mainActivity.resources.getString(navigationMenu.title), actual.title)
        assertEquals(navigationMenu.ordinal, actual.itemId)
        assertEquals(navigationMenu.ordinal, actual.order)
    }

}