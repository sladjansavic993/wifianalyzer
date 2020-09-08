//SS
package com.sladjan.wifianalyzer.wifi.filter.adapter

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.sladjan.wifianalyzer.settings.Settings
import com.sladjan.wifianalyzer.wifi.model.Strength
import org.junit.After
import org.junit.Assert.*
import org.junit.Test

class StrengthAdapterTest {
    private val settings: Settings = mock()
    private val fixture = StrengthAdapter(Strength.values().toSet())

    @After
    fun tearDown() {
        verifyNoMoreInteractions(settings)
    }

    @Test
    fun testIsActive() {
        assertFalse(fixture.isActive())
    }

    @Test
    fun testIsActiveWithChanges() {
        // setup
        fixture.toggle(Strength.TWO)
        // execute & validate
        assertTrue(fixture.isActive())
    }

    @Test
    fun testGetValues() {
        // setup
        val expected = Strength.values()
        // execute
        val actual = fixture.selections
        // validate
        assertTrue(actual.containsAll(expected.toList()))
    }

    @Test
    fun testGetValuesDefault() {
        // setup
        val expected = Strength.values()
        // execute
        val actual = fixture.defaults
        // validate
        assertArrayEquals(expected, actual)
    }

    @Test
    fun testToggleRemoves() {
        // execute
        val actual = fixture.toggle(Strength.TWO)
        // validate
        assertTrue(actual)
        assertFalse(fixture.contains(Strength.TWO))
    }

    @Test
    fun testToggleAdds() {
        // setup
        fixture.toggle(Strength.THREE)
        // execute
        val actual = fixture.toggle(Strength.THREE)
        // validate
        assertTrue(actual)
        assertTrue(fixture.contains(Strength.THREE))
    }

    @Test
    fun testRemovingAllWillNotRemoveLast() {
        // setup
        val values: Set<Strength> = Strength.values().toSet()
        // execute
        values.forEach { fixture.toggle(it) }
        // validate
        values.toList().subList(0, values.size - 1).forEach { assertFalse(fixture.contains(it)) }
        assertTrue(fixture.contains(values.last()))
    }

    @Test
    fun testGetColorWithExisting() {
        // execute & validate
        assertEquals(Strength.TWO.colorResource(), fixture.color(Strength.TWO))
    }

    @Test
    fun testGetColorWithNonExisting() {
        // setup
        fixture.toggle(Strength.TWO)
        // execute & validate
        assertEquals(Strength.colorResourceDefault, fixture.color(Strength.TWO))
    }

    @Test
    fun testSave() {
        // setup
        val expected = fixture.selections
        // execute
        fixture.save(settings)
        // execute
        verify(settings).saveStrengths(expected)
    }
}

