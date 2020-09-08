//SS
package com.sladjan.wifianalyzer.settings

import com.nhaarman.mockitokotlin2.*
import com.sladjan.wifianalyzer.R
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class SettingsAndroidOTest {
    private val scanSpeedDefault = 5
    private val repository: Repository = mock()
    private val fixture = spy(Settings(repository))

    @Before
    fun setUp() {
        doReturn(false).whenever(fixture).minVersionQ()
        doReturn(false).whenever(fixture).versionP()
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun testScanSpeed() {
        // setup
        val defaultValue = 10
        val expected = 3
        whenever(repository.stringAsInteger(R.string.scan_speed_default, scanSpeedDefault)).thenReturn(defaultValue)
        whenever(repository.stringAsInteger(R.string.scan_speed_key, defaultValue)).thenReturn(expected)
        // execute
        val actual = fixture.scanSpeed()
        // validate
        assertEquals(expected, actual)
        verify(repository).stringAsInteger(R.string.scan_speed_default, scanSpeedDefault)
        verify(repository).stringAsInteger(R.string.scan_speed_key, defaultValue)
        verify(fixture, never()).wiFiThrottleDisabled()
    }

    @Test
    fun testWiFiThrottleDisabled() {
        // execute
        val actual = fixture.wiFiThrottleDisabled()
        // validate
        assertFalse(actual)
        verify(repository, never()).resourceBoolean(any())
        verify(repository, never()).boolean(any(), any())
    }
}