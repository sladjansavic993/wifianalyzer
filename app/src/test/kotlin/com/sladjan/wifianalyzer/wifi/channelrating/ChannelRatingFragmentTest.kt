//SS
package com.sladjan.wifianalyzer.wifi.channelrating

import android.os.Build
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.MainContextHelper.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.RobolectricUtil
import com.sladjan.wifianalyzer.wifi.scanner.ScannerService
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class ChannelRatingFragmentTest {
    private val mainActivity: MainActivity = RobolectricUtil.INSTANCE.activity
    private val scanner: ScannerService = INSTANCE.scannerService
    private val fixture: ChannelRatingFragment = ChannelRatingFragment()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
        INSTANCE.restore()
    }

    @Test
    fun testOnCreateView() {
        // execute
        RobolectricUtil.INSTANCE.startFragment(fixture)
        // validate
        verify(scanner).update()
        verify(scanner).register(fixture.channelRatingAdapter)
    }

    @Test
    fun testOnResume() {
        // setup
        RobolectricUtil.INSTANCE.startFragment(fixture)
        // execute
        fixture.onResume()
        // validate
        verify(scanner, times(2)).update()
    }

    @Test
    fun testOnDestroy() {
        // setup
        RobolectricUtil.INSTANCE.startFragment(fixture)
        // execute
        fixture.onDestroy()
        // validate
        verify(scanner).unregister(fixture.channelRatingAdapter)
    }

    @Test
    fun testRefreshDisabled() {
        // setup
        RobolectricUtil.INSTANCE.startFragment(fixture)
        // execute
        val swipeRefreshLayout: SwipeRefreshLayout = fixture.view!!.findViewById(R.id.channelRatingRefresh)
        // validate
        assertFalse(swipeRefreshLayout.isRefreshing)
        assertFalse(swipeRefreshLayout.isEnabled)
    }
}