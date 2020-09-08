//SS
package com.sladjan.wifianalyzer.wifi.filter.adapter

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.settings.Settings
import com.sladjan.wifianalyzer.wifi.model.Security
import org.junit.After
import org.junit.Assert.*
import org.junit.Test

class SecurityAdapterTest {
    private val settings: Settings = mock()
    private val fixture = SecurityAdapter(Security.values().toSet())

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
        fixture.toggle(Security.WPA)
        // execute & validate
        assertTrue(fixture.isActive())
    }

    @Test
    fun testGetValues() {
        // setup
        val expected = Security.values()
        // execute
        val actual = fixture.selections
        // validate
        assertTrue(actual.containsAll(expected.toList()))
    }

    @Test
    fun testGetValuesDefault() {
        // setup
        val expected = Security.values()
        // execute
        val actual = fixture.defaults
        // validate
        assertArrayEquals(expected, actual)
    }

    @Test
    fun testToggleRemoves() {
        // execute
        val actual = fixture.toggle(Security.WEP)
        // validate
        assertTrue(actual)
        assertFalse(fixture.contains(Security.WEP))
    }

    @Test
    fun testToggleAdds() {
        // setup
        fixture.toggle(Security.WPA)
        // execute
        val actual = fixture.toggle(Security.WPA)
        // validate
        assertTrue(actual)
        assertTrue(fixture.contains(Security.WPA))
    }

    @Test
    fun testRemovingAllWillNotRemoveLast() {
        // setup
        val values: Set<Security> = Security.values().toSet()
        // execute
        values.forEach { fixture.toggle(it) }
        // validate
        values.forEach { fixture.contains(it) }
        assertTrue(fixture.contains(values.last()))
    }

    @Test
    fun testGetColorWithExisting() {
        // execute & validate
        assertEquals(R.color.selected, fixture.color(Security.WPA))
    }

    @Test
    fun testGetColorWithNonExisting() {
        // setup
        fixture.toggle(Security.WPA)
        // execute & validate
        assertEquals(R.color.regular, fixture.color(Security.WPA))
    }

    @Test
    fun testSave() {
        // setup
        val expected = fixture.selections
        // execute
        fixture.save(settings)
        // validate
        verify(settings).saveSecurities(expected)
    }

}