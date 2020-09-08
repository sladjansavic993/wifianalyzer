//SS
package com.sladjan.wifianalyzer.wifi.timegraph

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sladjan.wifianalyzer.RobolectricUtil
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class TimeGraphAdapterTest {

    @Test
    fun testGraphViewNotifiers() {
        // setup
        RobolectricUtil.INSTANCE.activity
        val fixture = TimeGraphAdapter()
        // execute
        val graphViewNotifiers = fixture.graphViewNotifiers()
        // validate
        assertEquals(WiFiBand.values().size, graphViewNotifiers.size)
    }

    @Test
    fun testGraphViews() {
        // setup
        RobolectricUtil.INSTANCE.activity
        val fixture = TimeGraphAdapter()
        // execute
        val graphViews = fixture.graphViews()
        // validate
        assertEquals(WiFiBand.values().size, graphViews.size)
    }
}