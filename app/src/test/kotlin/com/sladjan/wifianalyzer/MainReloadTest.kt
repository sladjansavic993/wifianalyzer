
package com.sladjan.wifianalyzer

import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.sladjan.wifianalyzer.settings.ThemeStyle
import com.sladjan.wifianalyzer.wifi.accesspoint.ConnectionViewType
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class MainReloadTest {
    private val settings = MainContextHelper.INSTANCE.settings
    private lateinit var fixture: MainReload

    @Before
    fun setUp() {
        whenever(settings.themeStyle()).thenReturn(ThemeStyle.DARK)
        whenever(settings.connectionViewType()).thenReturn(ConnectionViewType.COMPLETE)
        whenever(settings.languageLocale()).thenReturn(Locale.UK)
        fixture = MainReload(settings)
    }

    @After
    fun tearDown() {
        verify(settings, atLeastOnce()).themeStyle()
        verify(settings, atLeastOnce()).connectionViewType()
        verify(settings, atLeastOnce()).languageLocale()
        verifyNoMoreInteractions(settings)
        MainContextHelper.INSTANCE.restore()
    }

    @Test
    fun testShouldNotReloadWithNoThemeChanges() {
        // execute
        val actual = fixture.shouldReload(settings)
        // validate
        assertFalse(actual)
        assertEquals(ThemeStyle.DARK, fixture.themeStyle)
    }

    @Test
    fun testShouldReloadWithThemeChange() {
        // setup
        val expected = ThemeStyle.LIGHT
        whenever(settings.themeStyle()).thenReturn(expected)
        // execute
        val actual = fixture.shouldReload(settings)
        // validate
        assertTrue(actual)
        assertEquals(expected, fixture.themeStyle)
    }

    @Test
    fun testShouldNotReloadWithNoConnectionViewTypeChanges() {
        // execute
        val actual = fixture.shouldReload(settings)
        // validate
        assertFalse(actual)
        assertEquals(ConnectionViewType.COMPLETE, fixture.connectionViewType)
    }

    @Test
    fun testShouldReloadWithConnectionViewTypeChange() {
        // setup
        val expected = ConnectionViewType.COMPACT
        whenever(settings.connectionViewType()).thenReturn(expected)
        // execute
        val actual = fixture.shouldReload(settings)
        // validate
        assertTrue(actual)
        assertEquals(expected, fixture.connectionViewType)
    }

    @Test
    fun testShouldNotReloadWithNoLanguageLocaleChanges() {
        // execute
        val actual = fixture.shouldReload(settings)
        // validate
        assertFalse(actual)
        assertEquals(Locale.UK, fixture.languageLocale)
    }

    @Test
    fun testShouldReloadWithLanguageLocaleChange() {
        // setup
        val expected = Locale.US
        whenever(settings.languageLocale()).thenReturn(expected)
        // execute
        val actual = fixture.shouldReload(settings)
        // validate
        assertTrue(actual)
        assertEquals(expected, fixture.languageLocale)
    }

}