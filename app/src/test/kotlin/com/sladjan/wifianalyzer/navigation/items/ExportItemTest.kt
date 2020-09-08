
package com.sladjan.wifianalyzer.navigation.items

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.view.MenuItem
import android.view.View
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.MainContextHelper.INSTANCE
import com.sladjan.wifianalyzer.export.Export
import com.sladjan.wifianalyzer.navigation.NavigationMenu
import com.sladjan.wifianalyzer.wifi.model.WiFiConnection
import com.sladjan.wifianalyzer.wifi.model.WiFiData
import com.sladjan.wifianalyzer.wifi.model.WiFiDetail
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class ExportItemTest {
    private val export: Export = mock()
    private val mainActivity: MainActivity = mock()
    private val menuItem: MenuItem = mock()
    private val intent: Intent = mock()
    private val packageManager: PackageManager = mock()
    private val componentName: ComponentName = mock()
    private val scanner = INSTANCE.scannerService

    private val fixture = ExportItem(export)

    @After
    fun tearDown() {
        verifyNoMoreInteractions(export)
        verifyNoMoreInteractions(mainActivity)
        verifyNoMoreInteractions(menuItem)
        verifyNoMoreInteractions(intent)
        verifyNoMoreInteractions(packageManager)
        verifyNoMoreInteractions(componentName)
        verifyNoMoreInteractions(scanner)
        INSTANCE.restore()
    }

    @Test
    fun testActivate() {
        // setup
        val wiFiData: WiFiData = withWiFiData()
        whenever(scanner.wiFiData()).thenReturn(wiFiData)
        whenever(export.export(mainActivity, wiFiData.wiFiDetails)).thenReturn(intent)
        whenever(mainActivity.packageManager).thenReturn(packageManager)
        whenever(intent.resolveActivity(packageManager)).thenReturn(componentName)
        // execute
        fixture.activate(mainActivity, menuItem, NavigationMenu.EXPORT)
        // validate
        verify(scanner).wiFiData()
        verify(mainActivity).packageManager
        verify(intent).resolveActivity(packageManager)
        verify(mainActivity).startActivity(intent)
        verify(export).export(mainActivity, wiFiData.wiFiDetails)
    }

    @Test
    fun testRegistered() {
        // execute & validate
        assertFalse(fixture.registered)
    }

    @Test
    fun testVisibility() {
        // execute & validate
        assertEquals(View.GONE, fixture.visibility)
    }

    private fun withWiFiData(): WiFiData {
        return WiFiData(listOf(WiFiDetail.EMPTY), WiFiConnection.EMPTY)
    }

}