
package com.sladjan.wifianalyzer.wifi.model

import com.sladjan.wifianalyzer.wifi.band.WiFiWidth
import org.junit.Assert.*
import org.junit.Test

class GroupByTest {
    @Test
    fun testGroupByNumber() {
        assertEquals(3, GroupBy.values().size)
    }

    @Test
    fun testGroupBySort() {
        assertTrue(GroupBy.CHANNEL.sort.javaClass.isInstance(sortByChannel()))
        assertTrue(GroupBy.NONE.sort.javaClass.isInstance(sortByDefault()))
        assertTrue(GroupBy.SSID.sort.javaClass.isInstance(sortBySSID()))
    }

    @Test
    fun testGroupByGroup() {
        assertTrue(GroupBy.CHANNEL.group.javaClass.isInstance(groupByChannel))
        assertTrue(GroupBy.NONE.group.javaClass.isInstance(groupBySSID))
        assertTrue(GroupBy.SSID.group.javaClass.isInstance(groupBySSID))
    }

    @Test
    fun testNone() {
        assertFalse(GroupBy.CHANNEL.none)
        assertTrue(GroupBy.NONE.none)
        assertFalse(GroupBy.SSID.none)
    }

    @Test
    fun testGroupByKeyWithNone() {
        // setup
        val expected = "SSID_TO_TEST"
        val wiFiDetail = WiFiDetail(WiFiIdentifier(expected))
        // execute
        val actual: String = GroupBy.NONE.group(wiFiDetail)
        // validate
        assertEquals(expected, actual)
    }

    @Test
    fun testGroupByKeyWithSSID() {
        // setup
        val expected = "SSID_TO_TEST"
        val wiFiDetail = WiFiDetail(WiFiIdentifier(expected))
        // execute
        val actual: String = GroupBy.SSID.group(wiFiDetail)
        // validate
        assertEquals(expected, actual)
    }

    @Test
    fun testGroupByKeyWithChannel() {
        // setup
        val expected = "6"
        val wiFiDetail = WiFiDetail(
                WiFiIdentifier("xyzSSID", "xyzBSSID"),
                "WPA-WPA2",
                WiFiSignal(2435, 2435, WiFiWidth.MHZ_20, -40, true))
        // execute
        val actual: String = GroupBy.CHANNEL.group(wiFiDetail)
        // validate
        assertEquals(expected, actual)
    }
}