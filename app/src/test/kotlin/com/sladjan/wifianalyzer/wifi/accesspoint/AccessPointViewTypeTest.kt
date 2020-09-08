//SS
package com.sladjan.wifianalyzer.wifi.accesspoint

import com.sladjan.wifianalyzer.R
import org.junit.Assert.assertEquals
import org.junit.Test

class AccessPointViewTypeTest {
    @Test
    fun testAccessPointViewCount() {
        assertEquals(2, AccessPointViewType.values().size)
    }

    @Test
    fun testGetLayout() {
        assertEquals(R.layout.access_point_view_complete, AccessPointViewType.COMPLETE.layout)
        assertEquals(R.layout.access_point_view_compact, AccessPointViewType.COMPACT.layout)
    }

}