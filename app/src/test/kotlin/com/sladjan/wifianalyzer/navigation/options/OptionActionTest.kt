//SS
package com.sladjan.wifianalyzer.navigation.options

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.MainContextHelper.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.RobolectricUtil
import com.sladjan.wifianalyzer.navigation.options.OptionAction.Companion.findOptionAction
import com.sladjan.wifianalyzer.settings.Settings
import com.sladjan.wifianalyzer.wifi.scanner.ScannerService
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class OptionActionTest {
    private val mainActivity: MainActivity = RobolectricUtil.INSTANCE.activity
    private val scannerService: ScannerService = INSTANCE.scannerService
    private val settings: Settings = INSTANCE.settings

    @After
    fun tearDown() {
        verifyNoMoreInteractions(scannerService)
        verifyNoMoreInteractions(settings)
        INSTANCE.restore()
    }

    @Test
    fun testScannerAction() {
        // execute
        scannerAction()
        // validate
        verify(scannerService).toggle()
    }

    @Test
    fun testWiFiBandAction() {
        // execute
        wiFiBandAction()
        // validate
        verify(settings).toggleWiFiBand()
    }

    @Test
    fun testFilterAction() {
        filterAction()
    }

    @Test
    fun testOptionAction() {
        assertEquals(4, OptionAction.values().size)
    }

    @Test
    fun testGetKey() {
        assertEquals(-1, OptionAction.NO_ACTION.key)
        assertEquals(R.id.action_scanner, OptionAction.SCANNER.key)
        assertEquals(R.id.action_filter, OptionAction.FILTER.key)
        assertEquals(R.id.action_wifi_band, OptionAction.WIFI_BAND.key)
    }

    @Test
    fun testGetAction() {
        Assert.assertTrue(OptionAction.NO_ACTION.action == noAction)
        Assert.assertTrue(OptionAction.SCANNER.action == scannerAction)
        Assert.assertTrue(OptionAction.FILTER.action == filterAction)
        Assert.assertTrue(OptionAction.WIFI_BAND.action == wiFiBandAction)
    }

    @Test
    fun testGetOptionAction() {
        assertEquals(OptionAction.NO_ACTION, findOptionAction(OptionAction.NO_ACTION.key))
        assertEquals(OptionAction.SCANNER, findOptionAction(OptionAction.SCANNER.key))
        assertEquals(OptionAction.FILTER, findOptionAction(OptionAction.FILTER.key))
        assertEquals(OptionAction.WIFI_BAND, findOptionAction(OptionAction.WIFI_BAND.key))
    }

    @Test
    fun testGetOptionActionInvalidKey() {
        assertEquals(OptionAction.NO_ACTION, findOptionAction(-99))
        assertEquals(OptionAction.NO_ACTION, findOptionAction(99))
    }
}