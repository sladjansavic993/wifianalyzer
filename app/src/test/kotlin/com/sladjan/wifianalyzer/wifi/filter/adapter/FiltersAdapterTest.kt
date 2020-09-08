//SS
package com.sladjan.wifianalyzer.wifi.filter.adapter

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.sladjan.wifianalyzer.MainContextHelper.INSTANCE
import com.sladjan.wifianalyzer.RobolectricUtil
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.model.Security
import com.sladjan.wifianalyzer.wifi.model.Strength
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.io.Serializable

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class FiltersAdapterTest {
    private val mainActivity = RobolectricUtil.INSTANCE.activity
    private val ssids = setOf<String>()
    private val wiFiBands: Set<WiFiBand> = WiFiBand.values().toSet()
    private val strengths: Set<Strength> = Strength.values().toSet()
    private val securities: Set<Security> = Security.values().toSet()
    private val settings = INSTANCE.settings

    private lateinit var fixture: FiltersAdapter

    @Before
    fun setUp() {
        whenever(settings.findSSIDs()).thenReturn(ssids)
        whenever(settings.findWiFiBands()).thenReturn(wiFiBands)
        whenever(settings.findStrengths()).thenReturn(strengths)
        whenever(settings.findSecurities()).thenReturn(securities)

        fixture = FiltersAdapter(settings)

        verify(settings).findSSIDs()
        verify(settings).findWiFiBands()
        verify(settings).findStrengths()
        verify(settings).findSecurities()
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(settings)
        INSTANCE.restore()
    }

    @Test
    fun testIsActive() {
        // execute & validate
        assertFalse(fixture.isActive())
    }

    @Test
    fun testGetFilterAdapters() {
        // execute
        val actual: List<BasicFilterAdapter<out Serializable?>?> = fixture.filterAdapters(true)
        // validate
        assertEquals(4, actual.size)
    }

    @Test
    fun testGetFilterAdaptersWithNptAccessPoints() {
        // execute
        val actual: List<BasicFilterAdapter<out Serializable?>?> = fixture.filterAdapters(false)
        // validate
        assertEquals(3, actual.size)
    }

    @Test
    fun testIsActiveWhenStrengthFilterIsChanged() {
        // setup
        fixture.strengthAdapter().toggle(Strength.THREE)
        // execute & validate
        assertTrue(fixture.isActive())
    }

    @Test
    fun testIsActiveWhenWiFiBandFilterIsChanged() {
        // setup
        fixture.wiFiBandAdapter().toggle(WiFiBand.GHZ2)
        // execute & validate
        assertTrue(fixture.isActive())
    }

    @Test
    fun testReset() {
        // execute
        fixture.reset()
        // validate
        verify(settings).saveSSIDs(ssids)
        verify(settings).saveWiFiBands(wiFiBands)
        verify(settings).saveStrengths(strengths)
        verify(settings).saveSecurities(securities)
    }

    @Test
    fun testReload() {
        // execute
        fixture.reload()
        // validate
        verify(settings, times(2)).findSSIDs()
        verify(settings, times(2)).findWiFiBands()
        verify(settings, times(2)).findStrengths()
        verify(settings, times(2)).findSecurities()
    }

    @Test
    fun testSave() {
        // execute
        fixture.save()
        // validate
        verify(settings).saveSSIDs(ssids)
        verify(settings).saveWiFiBands(wiFiBands)
        verify(settings).saveStrengths(strengths)
        verify(settings).saveSecurities(securities)
    }
}