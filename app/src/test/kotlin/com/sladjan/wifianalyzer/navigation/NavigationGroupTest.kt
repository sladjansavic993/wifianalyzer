//SS
package com.sladjan.wifianalyzer.navigation

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class NavigationGroupTest {
    @Test
    fun testNavigationGroup() {
        assertEquals(3, NavigationGroup.values().size)
    }

    @Test
    fun testNavigationGroupOrder() {
        // setup
        val expected = arrayOf(
                NavigationGroup.GROUP_FEATURE,
                NavigationGroup.GROUP_OTHER,
                NavigationGroup.GROUP_SETTINGS)
        // validate
        assertArrayEquals(expected, NavigationGroup.values())
    }

    @Test
    fun testSettingsNavigationMenus() {
        // setup
        val expected: List<NavigationMenu> = listOf(NavigationMenu.SETTINGS, NavigationMenu.ABOUT)
        // validate
        assertEquals(expected, NavigationGroup.GROUP_SETTINGS.navigationMenus)
    }

    @Test
    fun testFeatureNavigationMenus() {
        // setup
        val expected: List<NavigationMenu> = listOf(
                NavigationMenu.ACCESS_POINTS,
                NavigationMenu.CHANNEL_RATING,
                NavigationMenu.CHANNEL_GRAPH,
                NavigationMenu.TIME_GRAPH)
        // validate
        assertEquals(expected, NavigationGroup.GROUP_FEATURE.navigationMenus)
    }

    @Test
    fun testOtherNavigationMenus() {
        // setup
        val expected: List<NavigationMenu> = listOf(
                NavigationMenu.EXPORT,
                NavigationMenu.CHANNEL_AVAILABLE,
                NavigationMenu.VENDORS,
                NavigationMenu.PORT_AUTHORITY)
        // validate
        assertEquals(expected, NavigationGroup.GROUP_OTHER.navigationMenus)
    }

    @Test
    fun testNext() {
        assertEquals(NavigationMenu.CHANNEL_GRAPH, NavigationGroup.GROUP_FEATURE.next(NavigationMenu.CHANNEL_RATING))
        assertEquals(NavigationMenu.ACCESS_POINTS, NavigationGroup.GROUP_FEATURE.next(NavigationMenu.TIME_GRAPH))
        assertEquals(NavigationMenu.EXPORT, NavigationGroup.GROUP_FEATURE.next(NavigationMenu.EXPORT))
    }

    @Test
    fun testPrevious() {
        assertEquals(NavigationMenu.ACCESS_POINTS, NavigationGroup.GROUP_FEATURE.previous(NavigationMenu.CHANNEL_RATING))
        assertEquals(NavigationMenu.TIME_GRAPH, NavigationGroup.GROUP_FEATURE.previous(NavigationMenu.ACCESS_POINTS))
        assertEquals(NavigationMenu.EXPORT, NavigationGroup.GROUP_FEATURE.next(NavigationMenu.EXPORT))
    }
}