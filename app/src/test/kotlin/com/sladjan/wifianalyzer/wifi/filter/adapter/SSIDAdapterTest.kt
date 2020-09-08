//SS
package com.sladjan.wifianalyzer.wifi.filter.adapter

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.sladjan.wifianalyzer.settings.Settings
import org.junit.After
import org.junit.Assert.*
import org.junit.Test

class SSIDAdapterTest {
    private val ssidValues: Set<String> = setOf("value1", "value2", "value3")
    private val settings: Settings = mock()
    private val fixture = SSIDAdapter(ssidValues)

    @After
    fun tearDown() {
        verifyNoMoreInteractions(settings)
    }

    @Test
    fun testGetValues() {
        // setup
        // execute
        val actual = fixture.selections
        // validate
        assertTrue(actual.containsAll(ssidValues))
    }

    @Test
    fun testIsActive() {
        assertTrue(fixture.isActive())
    }

    @Test
    fun testIsNotActiveWithEmptyValue() {
        // execute
        fixture.selections = setOf()
        // validate
        assertFalse(fixture.isActive())
        assertTrue(fixture.selections.isEmpty())
    }

    @Test
    fun testIsNotActiveWithReset() {
        // execute
        fixture.reset()
        // validate
        assertFalse(fixture.isActive())
        assertTrue(fixture.selections.isEmpty())
    }

    @Test
    fun testSave() {
        // execute
        fixture.save(settings)
        // execute
        verify(settings).saveSSIDs(ssidValues)
    }

    @Test
    fun testSetValues() {
        // setup
        val expected: Set<String> = setOf("ABC", "EDF", "123")
        val values: Set<String> = setOf("", "ABC", "", "EDF", "  ", "123", "")
        // execute
        fixture.selections = values
        // execute
        assertEquals(expected, fixture.selections)
    }

}