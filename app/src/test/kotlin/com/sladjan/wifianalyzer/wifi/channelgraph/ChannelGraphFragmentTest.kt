//SS
package com.sladjan.wifianalyzer.wifi.channelgraph

import android.os.Build
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.sladjan.wifianalyzer.MainContextHelper
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.RobolectricUtil
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class ChannelGraphFragmentTest {
    private val mainActivity = RobolectricUtil.INSTANCE.activity
    private val scanner = MainContextHelper.INSTANCE.scannerService
    private val fixture = ChannelGraphFragment()

    @Before
    fun setUp() {
        RobolectricUtil.INSTANCE.startFragment(fixture)
    }

    @After
    fun tearDown() {
        MainContextHelper.INSTANCE.restore()
    }

    @Test
    fun testOnCreateView() {
        // validate
        assertNotNull(fixture)
        verify(scanner).update()
        verify(scanner).register(fixture.channelGraphAdapter)
    }

    @Test
    fun testOnResume() {
        // execute
        fixture.onResume()
        // validate
        verify(scanner, times(2)).update()
    }

    @Test
    fun testOnDestroy() {
        // execute
        fixture.onDestroy()
        // validate
        verify(scanner).unregister(fixture.channelGraphAdapter)
    }

    @Test
    fun testRefreshDisabled() {
        // validate
        val swipeRefreshLayout: SwipeRefreshLayout = fixture.view!!.findViewById(R.id.graphRefresh)
        assertFalse(swipeRefreshLayout.isRefreshing)
        assertFalse(swipeRefreshLayout.isEnabled)
    }
}