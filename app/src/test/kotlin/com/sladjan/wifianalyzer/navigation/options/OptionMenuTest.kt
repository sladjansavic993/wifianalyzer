//SS
package com.sladjan.wifianalyzer.navigation.options

import android.app.Activity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.sladjan.wifianalyzer.R
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class OptionMenuTest {
    private val menu: Menu = mock()
    private val menuItem: MenuItem = mock()
    private val activity: Activity = mock()
    private val menuInflater: MenuInflater = mock()
    private val fixture = OptionMenu()

    @After
    fun tearDown() {
        verifyNoMoreInteractions(menu)
        verifyNoMoreInteractions(menuItem)
        verifyNoMoreInteractions(activity)
        verifyNoMoreInteractions(menuInflater)
    }

    @Test
    fun testCreate() {
        // setup
        whenever(activity.menuInflater).thenReturn(menuInflater)
        // execute
        fixture.create(activity, menu)
        // validate
        assertEquals(menu, fixture.menu)
        verify(activity).menuInflater
        verify(menuInflater).inflate(R.menu.optionmenu, menu)
    }

    @Test
    fun testActions() {
        // setup
        val itemId = -1
        whenever(menuItem.itemId).thenReturn(itemId)
        // execute
        fixture.select(menuItem)
        // validate
        verify(menuItem).itemId
    }
}