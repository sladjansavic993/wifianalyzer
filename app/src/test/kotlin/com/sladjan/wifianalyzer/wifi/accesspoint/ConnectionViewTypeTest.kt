//SS
package com.sladjan.wifianalyzer.wifi.accesspoint

import com.sladjan.wifianalyzer.R
import org.junit.Assert.*
import org.junit.Test

class ConnectionViewTypeTest {
    @Test
    fun testConnectionViewTypeCount() {
        assertEquals(3, ConnectionViewType.values().size)
    }

    @Test
    fun testGetLayout() {
        assertEquals(R.layout.access_point_view_complete, ConnectionViewType.COMPLETE.layout)
        assertEquals(R.layout.access_point_view_compact, ConnectionViewType.COMPACT.layout)
        assertEquals(R.layout.access_point_view_hide, ConnectionViewType.HIDE.layout)
    }

    @Test
    fun testIsHide() {
        assertFalse(ConnectionViewType.COMPLETE.hide)
        assertFalse(ConnectionViewType.COMPACT.hide)
        assertTrue(ConnectionViewType.HIDE.hide)
    }

}