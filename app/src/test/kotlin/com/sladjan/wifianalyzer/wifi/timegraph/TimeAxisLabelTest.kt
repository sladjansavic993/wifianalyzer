//SS
package com.sladjan.wifianalyzer.wifi.timegraph

import com.sladjan.util.EMPTY
import com.sladjan.wifianalyzer.wifi.graphutils.MAX_Y
import com.sladjan.wifianalyzer.wifi.graphutils.MIN_Y
import org.junit.Assert.assertEquals
import org.junit.Test

class TimeAxisLabelTest {
    private val fixture = TimeAxisLabel()

    @Test
    fun testYAxis() {
        assertEquals(String.EMPTY, fixture.formatLabel(MIN_Y.toDouble(), false))
        assertEquals("-99", fixture.formatLabel(MIN_Y + 1.toDouble(), false))
        assertEquals("0", fixture.formatLabel(MAX_Y.toDouble(), false))
        assertEquals(String.EMPTY, fixture.formatLabel(MAX_Y + 1.toDouble(), false))
    }

    @Test
    fun testXAxis() {
        assertEquals(String.EMPTY, fixture.formatLabel(-2.0, true))
        assertEquals(String.EMPTY, fixture.formatLabel(-1.0, true))
        assertEquals(String.EMPTY, fixture.formatLabel(0.0, true))
        assertEquals(String.EMPTY, fixture.formatLabel(1.0, true))
        assertEquals("2", fixture.formatLabel(2.0, true))
        assertEquals("10", fixture.formatLabel(10.0, true))
    }
}