//SS
package com.sladjan.wifianalyzer.navigation.availability

import org.junit.Assert.*
import org.junit.Test

class NavigationOptionsTest {
    @Test
    fun testRating() {
        val options: List<NavigationOption> = navigationOptionRating
        assertEquals(4, options.size)
        assertTrue(options.contains(navigationOptionWiFiSwitchOn))
        assertTrue(options.contains(navigationOptionScannerSwitchOn))
        assertTrue(options.contains(navigationOptionFilterOff))
        assertTrue(options.contains(navigationOptionBottomNavOn))
        assertFalse(options.contains(navigationOptionWiFiSwitchOff))
        assertFalse(options.contains(navigationOptionScannerSwitchOff))
        assertFalse(options.contains(navigationOptionFilterOn))
        assertFalse(options.contains(navigationOptionBottomNavOff))
    }

    @Test
    fun testOther() {
        val options: List<NavigationOption> = navigationOptionOther
        assertEquals(4, options.size)
        assertTrue(options.contains(navigationOptionWiFiSwitchOn))
        assertTrue(options.contains(navigationOptionScannerSwitchOn))
        assertTrue(options.contains(navigationOptionFilterOn))
        assertTrue(options.contains(navigationOptionBottomNavOn))
        assertFalse(options.contains(navigationOptionWiFiSwitchOff))
        assertFalse(options.contains(navigationOptionScannerSwitchOff))
        assertFalse(options.contains(navigationOptionFilterOff))
        assertFalse(options.contains(navigationOptionBottomNavOff))
    }

    @Test
    fun testOff() {
        val options: List<NavigationOption> = navigationOptionOff
        assertEquals(4, options.size)
        assertTrue(options.contains(navigationOptionWiFiSwitchOff))
        assertTrue(options.contains(navigationOptionScannerSwitchOff))
        assertTrue(options.contains(navigationOptionFilterOff))
        assertTrue(options.contains(navigationOptionBottomNavOff))
        assertFalse(options.contains(navigationOptionWiFiSwitchOn))
        assertFalse(options.contains(navigationOptionScannerSwitchOn))
        assertFalse(options.contains(navigationOptionFilterOn))
        assertFalse(options.contains(navigationOptionBottomNavOn))
    }

    @Test
    fun testAccessPoints() {
        val options: List<NavigationOption> = navigationOptionAp
        assertEquals(4, options.size)
        assertTrue(options.contains(navigationOptionWiFiSwitchOff))
        assertTrue(options.contains(navigationOptionScannerSwitchOn))
        assertTrue(options.contains(navigationOptionFilterOn))
        assertTrue(options.contains(navigationOptionBottomNavOn))
        assertFalse(options.contains(navigationOptionWiFiSwitchOn))
        assertFalse(options.contains(navigationOptionScannerSwitchOff))
        assertFalse(options.contains(navigationOptionFilterOff))
        assertFalse(options.contains(navigationOptionBottomNavOff))
    }
}