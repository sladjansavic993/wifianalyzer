//SS
package com.sladjan.wifianalyzer.wifi.scanner

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Handler
import com.nhaarman.mockitokotlin2.*
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.settings.Settings
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Test

class ScannerServiceTest {
    private val mainActivity: MainActivity = mock()
    private val wifiManager: WifiManager = mock()
    private val handler: Handler = mock()
    private val settings: Settings = mock()
    private val context: Context = mock()

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mainActivity)
        verifyNoMoreInteractions(wifiManager)
        verifyNoMoreInteractions(handler)
        verifyNoMoreInteractions(settings)
        verifyNoMoreInteractions(context)
    }

    @Test
    fun testMakeScannerService() {
        // setup
        val delayInitial = 1L
        whenever(mainActivity.applicationContext).thenReturn(context)
        whenever(context.getSystemService(Context.WIFI_SERVICE)).thenReturn(wifiManager)
        // execute
        val actual = makeScannerService(mainActivity, handler, settings)
        // validate
        assertTrue(actual is Scanner)
        assertTrue(actual.running())
        verify(mainActivity).applicationContext
        verify(context).getSystemService(Context.WIFI_SERVICE)
        verify(handler).removeCallbacks(any())
        verify(handler).postDelayed(any(), eq(delayInitial))
    }
}