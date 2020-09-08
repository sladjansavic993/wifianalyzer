
package com.sladjan.wifianalyzer.navigation.items

import android.view.View
import com.sladjan.wifianalyzer.about.AboutFragment
import com.sladjan.wifianalyzer.settings.SettingsFragment
import com.sladjan.wifianalyzer.vendor.VendorFragment
import com.sladjan.wifianalyzer.wifi.accesspoint.AccessPointsFragment
import com.sladjan.wifianalyzer.wifi.channelavailable.ChannelAvailableFragment
import com.sladjan.wifianalyzer.wifi.channelgraph.ChannelGraphFragment
import com.sladjan.wifianalyzer.wifi.channelrating.ChannelRatingFragment
import com.sladjan.wifianalyzer.wifi.timegraph.TimeGraphFragment
import org.junit.Assert.*
import org.junit.Test

class NavigationItemsTest {
    @Test
    fun testFragmentItem() {
        assertTrue((navigationItemAccessPoints as FragmentItem).fragment is AccessPointsFragment)
        assertTrue((navigationItemChannelRating as FragmentItem).fragment is ChannelRatingFragment)
        assertTrue((navigationItemChannelGraph as FragmentItem).fragment is ChannelGraphFragment)
        assertTrue((navigationItemTimeGraph as FragmentItem).fragment is TimeGraphFragment)
        assertTrue((navigationItemChannelAvailable as FragmentItem).fragment is ChannelAvailableFragment)
        assertTrue((navigationItemVendors as FragmentItem).fragment is VendorFragment)
        assertTrue((navigationItemSettings as FragmentItem).fragment is SettingsFragment)
        assertTrue((navigationItemAbout as FragmentItem).fragment is AboutFragment)
    }

    @Test
    fun testRegisteredTrue() {
        assertTrue(navigationItemAccessPoints.registered)
        assertTrue(navigationItemChannelRating.registered)
        assertTrue(navigationItemChannelGraph.registered)
        assertTrue(navigationItemTimeGraph.registered)
    }

    @Test
    fun testRegisteredFalse() {
        assertFalse(navigationItemExport.registered)
        assertFalse(navigationItemChannelAvailable.registered)
        assertFalse(navigationItemVendors.registered)
        assertFalse(navigationItemSettings.registered)
        assertFalse(navigationItemAbout.registered)
    }

    @Test
    fun testVisibility() {
        assertEquals(View.VISIBLE, navigationItemAccessPoints.visibility)
        assertEquals(View.VISIBLE, navigationItemChannelRating.visibility)
        assertEquals(View.VISIBLE, navigationItemChannelGraph.visibility)
        assertEquals(View.VISIBLE, navigationItemTimeGraph.visibility)
        assertEquals(View.VISIBLE, navigationItemChannelAvailable.visibility)
        assertEquals(View.GONE, navigationItemVendors.visibility)
        assertEquals(View.GONE, navigationItemExport.visibility)
        assertEquals(View.GONE, navigationItemSettings.visibility)
        assertEquals(View.GONE, navigationItemAbout.visibility)
    }

    @Test
    fun testExportItem() {
        assertTrue(navigationItemExport is ExportItem)
    }
}