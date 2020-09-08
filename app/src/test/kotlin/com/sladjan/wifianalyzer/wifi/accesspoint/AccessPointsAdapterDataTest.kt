//SS
package com.sladjan.wifianalyzer.wifi.accesspoint

import android.widget.ExpandableListView
import com.nhaarman.mockitokotlin2.*
import com.sladjan.wifianalyzer.MainContextHelper.INSTANCE
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.band.WiFiWidth
import com.sladjan.wifianalyzer.wifi.model.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class AccessPointsAdapterDataTest {
    private val wiFiData: WiFiData = mock()
    private val accessPointsAdapterGroup: AccessPointsAdapterGroup = mock()
    private val expandableListView: ExpandableListView = mock()
    private val settings = INSTANCE.settings
    private val configuration = INSTANCE.configuration
    private val fixture = AccessPointsAdapterData(accessPointsAdapterGroup)

    @After
    fun tearDown() {
        verifyNoMoreInteractions(wiFiData)
        verifyNoMoreInteractions(accessPointsAdapterGroup)
        verifyNoMoreInteractions(expandableListView)
        verifyNoMoreInteractions(configuration)
        INSTANCE.restore()
    }

    @Test
    fun testBeforeUpdate() {
        assertEquals(0, fixture.parentsCount())
        assertEquals(0, fixture.childrenCount(0))
        assertEquals(WiFiDetail.EMPTY, fixture.parent(0))
        assertEquals(WiFiDetail.EMPTY, fixture.parent(-1))
        assertEquals(WiFiDetail.EMPTY, fixture.child(0, 0))
        assertEquals(WiFiDetail.EMPTY, fixture.child(0, -1))
    }

    @Test
    fun testAfterUpdateWithGroupByChannel() {
        // setup
        val wiFiDetails = withWiFiDetails()
        withSettings()
        whenever(wiFiData.wiFiDetails(any(), eq(SortBy.SSID), eq(GroupBy.CHANNEL))).thenReturn(wiFiDetails)
        // execute
        fixture.update(wiFiData, expandableListView)
        // validate
        verify(wiFiData).wiFiDetails(any(), eq(SortBy.SSID), eq(GroupBy.CHANNEL))
        verify(accessPointsAdapterGroup).update(wiFiDetails, expandableListView)
        verifySettings()
        assertEquals(wiFiDetails.size, fixture.parentsCount())
        assertEquals(wiFiDetails[0], fixture.parent(0))
        assertEquals(wiFiDetails[0].children.size, fixture.childrenCount(0))
        assertEquals(wiFiDetails[0].children[0], fixture.child(0, 0))
        assertEquals(WiFiDetail.EMPTY, fixture.parent(-1))
        assertEquals(WiFiDetail.EMPTY, fixture.parent(wiFiDetails.size))
        assertEquals(WiFiDetail.EMPTY, fixture.child(0, -1))
        assertEquals(WiFiDetail.EMPTY, fixture.child(0, wiFiDetails[0].children.size))
    }

    @Test
    fun testOnGroupCollapsed() {
        // setup
        val index = 11
        val wiFiDetails: List<WiFiDetail> = fixture.wiFiDetails
        // execute
        fixture.onGroupCollapsed(index)
        // validate
        verify(accessPointsAdapterGroup).onGroupCollapsed(wiFiDetails, index)
    }

    @Test
    fun testOnGroupExpanded() {
        // setup
        val index = 22
        val wiFiDetails: List<WiFiDetail> = fixture.wiFiDetails
        // execute
        fixture.onGroupExpanded(index)
        // validate
        verify(accessPointsAdapterGroup).onGroupExpanded(wiFiDetails, index)
    }

    private fun withWiFiDetail(): WiFiDetail =
            WiFiDetail(
                    WiFiIdentifier("SSID1", "BSSID1"),
                    wiFiSignal = WiFiSignal(2255, 2255, WiFiWidth.MHZ_20, -40, true),
                    children = listOf(WiFiDetail(WiFiIdentifier("SSID1-1", "BSSID1-1")),
                            WiFiDetail(WiFiIdentifier("SSID1-2", "BSSID1-2")),
                            WiFiDetail(WiFiIdentifier("SSID1-3", "BSSID1-3"))))

    private fun withWiFiDetails(): List<WiFiDetail> =
            listOf(withWiFiDetail(),
                    WiFiDetail(WiFiIdentifier("SSID2", "BSSID2")),
                    WiFiDetail(WiFiIdentifier("SSID3", "BSSID3")))

    private fun verifySettings() {
        verify(settings).sortBy()
        verify(settings).groupBy()
        verify(settings).findWiFiBands()
        verify(settings).findStrengths()
        verify(settings).findSecurities()
        verify(configuration).size = any()
    }

    private fun withSettings() {
        whenever(settings.sortBy()).thenReturn(SortBy.SSID)
        whenever(settings.groupBy()).thenReturn(GroupBy.CHANNEL)
        whenever(settings.findWiFiBands()).thenReturn(WiFiBand.values().toSet())
        whenever(settings.findStrengths()).thenReturn(Strength.values().toSet())
        whenever(settings.findSecurities()).thenReturn(Security.values().toSet())
    }

}