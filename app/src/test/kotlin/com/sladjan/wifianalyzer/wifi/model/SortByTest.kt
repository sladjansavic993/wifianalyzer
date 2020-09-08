//SS
package com.sladjan.wifianalyzer.wifi.model

import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Test

class SortByTest {
    @Test
    fun testSortByNumber() {
        Assert.assertEquals(3, SortBy.values().size)
    }

    @Test
    fun testComparator() {
        assertTrue(SortBy.STRENGTH.sort.javaClass.isInstance(sortByStrength()))
        assertTrue(SortBy.SSID.sort.javaClass.isInstance(sortBySSID()))
        assertTrue(SortBy.CHANNEL.sort.javaClass.isInstance(sortByChannel()))
    }
}