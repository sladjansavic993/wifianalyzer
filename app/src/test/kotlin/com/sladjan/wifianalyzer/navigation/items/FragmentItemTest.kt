//SS
package com.sladjan.wifianalyzer.navigation.items

import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.navigation.NavigationMenu
import org.junit.Assert.*
import org.junit.Test

class FragmentItemTest {
    private val title = "title"
    private val fragment: Fragment = mock()
    private val mainActivity: MainActivity = mock()
    private val menuItem: MenuItem = mock()
    private val fragmentManager: FragmentManager = mock()
    private val fragmentTransaction: FragmentTransaction = mock()

    @Test
    fun testActivateWithStateSaved() {
        // setup
        val fixture = FragmentItem(fragment, true, View.VISIBLE)
        val navigationMenu = NavigationMenu.ACCESS_POINTS
        whenever(mainActivity.supportFragmentManager).thenReturn(fragmentManager)
        whenever(fragmentManager.isStateSaved).thenReturn(true)
        // execute
        fixture.activate(mainActivity, menuItem, navigationMenu)
        // validate
        verify(mainActivity).supportFragmentManager
        verify(fragmentManager).isStateSaved
        verifyFragmentManagerIsNotCalled()
        verifyNoChangesToMainActivity(navigationMenu)
    }

    @Test
    fun testActivateWithStateNotSaved() {
        // setup
        val fixture = FragmentItem(fragment, true, View.VISIBLE)
        val navigationMenu = NavigationMenu.ACCESS_POINTS
        whenever(mainActivity.supportFragmentManager).thenReturn(fragmentManager)
        whenever(fragmentManager.isStateSaved).thenReturn(false)
        withFragmentTransaction()
        whenever(menuItem.title).thenReturn(title)
        // execute
        fixture.activate(mainActivity, menuItem, navigationMenu)
        // validate
        verify(mainActivity).supportFragmentManager
        verify(fragmentManager).isStateSaved
        verifyFragmentManager()
        verifyMainActivityChanges(navigationMenu)
    }

    @Test
    fun testRegisteredFalse() {
        // setup
        val fixture = FragmentItem(fragment, false, View.VISIBLE)
        // execute & validate
        assertFalse(fixture.registered)
    }

    @Test
    fun testRegisteredTrue() {
        // setup
        val fixture = FragmentItem(fragment, true, View.VISIBLE)
        // execute & validate
        assertTrue(fixture.registered)
    }

    @Test
    fun testVisibility() {
        // setup
        val fixture = FragmentItem(fragment, false, View.INVISIBLE)
        // execute & validate
        assertEquals(View.INVISIBLE, fixture.visibility)
    }

    private fun withFragmentTransaction() {
        whenever(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction)
        whenever(fragmentTransaction.replace(R.id.main_fragment, fragment)).thenReturn(fragmentTransaction)
    }

    private fun verifyFragmentManager() {
        verify(fragmentManager).beginTransaction()
        verify(fragmentTransaction).replace(R.id.main_fragment, fragment)
        verify(fragmentTransaction).commit()
    }

    private fun verifyMainActivityChanges(navigationMenu: NavigationMenu) {
        verify(mainActivity).currentNavigationMenu(navigationMenu)
        verify(mainActivity).title = title
        verify(mainActivity).updateActionBar()
    }

    private fun verifyFragmentManagerIsNotCalled() {
        verify(fragmentManager, never()).beginTransaction()
        verify(fragmentTransaction, never()).replace(R.id.main_fragment, fragment)
        verify(fragmentTransaction, never()).commit()
    }

    private fun verifyNoChangesToMainActivity(navigationMenu: NavigationMenu) {
        verify(mainActivity, never()).currentNavigationMenu(navigationMenu)
        verify(mainActivity, never()).title = title
        verify(mainActivity, never()).updateActionBar()
    }
}