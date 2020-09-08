//SS
package com.sladjan.wifianalyzer.settings

import com.nhaarman.mockitokotlin2.*
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class SettingsAndroidQTest {
    private val repository: Repository = mock()
    private val fixture = spy(Settings(repository))

    @Before
    fun setUp() {
        doReturn(true).whenever(fixture).minVersionQ()
        doReturn(false).whenever(fixture).versionP()
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun testWiFiOffOnExitAndroidQ() {
        // execute
        val actual = fixture.wiFiOffOnExit()
        // validate
        assertFalse(actual)
        verify(fixture).minVersionQ()
    }

    @Test
    fun testWiFiThrottleDisabled() {
        // execute
        val actual = fixture.wiFiThrottleDisabled()
        // validate
        assertFalse(actual)
        verify(repository, never()).resourceBoolean(any())
        verify(repository, never()).boolean(any(), any())
        verify(fixture).versionP()
    }
}