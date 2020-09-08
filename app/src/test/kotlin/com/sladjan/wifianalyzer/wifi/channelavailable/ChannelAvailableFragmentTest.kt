//SS
package com.sladjan.wifianalyzer.wifi.channelavailable

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sladjan.wifianalyzer.MainContextHelper.INSTANCE
import com.sladjan.wifianalyzer.RobolectricUtil
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.util.*

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class ChannelAvailableFragmentTest {
    private val mainActivity = RobolectricUtil.INSTANCE.activity
    private val settings = INSTANCE.settings
    private val fixture = ChannelAvailableFragment()

    @Before
    fun setUp() {
        whenever(settings.countryCode()).thenReturn(Locale.US.country)
    }

    @After
    fun tearDown() {
        verify(settings, atLeastOnce()).countryCode()
        INSTANCE.restore()
    }

    @Test
    fun testOnCreateView() {
        // setup
        RobolectricUtil.INSTANCE.startFragment(fixture)
        // validate
        assertNotNull(fixture)
    }
}