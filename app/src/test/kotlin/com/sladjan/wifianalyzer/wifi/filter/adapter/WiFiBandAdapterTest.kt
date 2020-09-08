//SS
package com.sladjan.wifianalyzer.wifi.filter.adapter

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.settings.Settings
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import org.junit.After
import org.junit.Assert.*
import org.junit.Test

class WiFiBandAdapterTest {
    private val settings: Settings = mock()
    private val fixture = WiFiBandAdapter(WiFiBand.values().toSet())

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
        fixture.toggle(WiFiBand.GHZ2)
        // execute & validate
        assertTrue(fixture.isActive())
    }

    @Test
    fun testGetValues() {
        // setup
        val expected = WiFiBand.values()
        // execute
        val actual = fixture.selections
        // validate
        assertTrue(actual.containsAll(expected.toList()))
    }

    @Test
    fun testGetValuesDefault() {
        // setup
        val expected = WiFiBand.values()
        // execute
        val actual = fixture.defaults
        // validate
        assertArrayEquals(expected, actual)
    }

    @Test
    fun testToggleRemoves() {
        // execute
        val actual = fixture.toggle(WiFiBand.GHZ2)
        // validate
        assertTrue(actual)
        assertFalse(fixture.contains(WiFiBand.GHZ2))
    }

    @Test
    fun testToggleAdds() {
        // setup
        fixture.toggle(WiFiBand.GHZ5)
        // execute
        val actual = fixture.toggle(WiFiBand.GHZ5)
        // validate
        assertTrue(actual)
        assertTrue(fixture.contains(WiFiBand.GHZ5))
    }

    @Test
    fun testRemovingAllWillNotRemoveLast() {
        // setup
        val values: Set<WiFiBand> = WiFiBand.values().toSet()
        // execute
        values.forEach { fixture.toggle(it) }
        // validate
        values.toList().subList(0, values.size - 1).forEach { assertFalse(fixture.contains(it)) }
        assertTrue(fixture.contains(values.last()))
    }

    @Test
    fun testGetColorWithExisting() {
        // execute & validate
        assertEquals(R.color.selected, fixture.color(WiFiBand.GHZ2))
    }

    @Test
    fun testGetColorWithNonExisting() {
        // setup
        fixture.toggle(WiFiBand.GHZ2)
        // execute & validate
        assertEquals(R.color.regular, fixture.color(WiFiBand.GHZ2))
    }

    @Test
    fun testSave() {
        // setup
        val expected = fixture.selections
        // execute
        fixture.save(settings)
        // execute
        verify(settings).saveWiFiBands(expected)
    }
}
