
package com.sladjan.wifianalyzer

import android.content.res.Configuration
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.nhaarman.mockitokotlin2.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class DrawerNavigationTest {
    private val mainActivity: MainActivity = mock()
    private val toolbar: Toolbar = mock()
    private val configuration: Configuration = mock()
    private val drawerLayout: DrawerLayout = mock()
    private val actionBarDrawerToggle: ActionBarDrawerToggle = mock()
    private val fixture = spy(DrawerNavigation(mainActivity, toolbar))

    @Before
    fun setUp() {
        doReturn(actionBarDrawerToggle).whenever(fixture).createDrawerToggle(drawerLayout)
        whenever<Any>(mainActivity.findViewById(R.id.drawer_layout)).thenReturn(drawerLayout)
        fixture.create()
    }

    @After
    fun tearDown() {
        verify(mainActivity).findViewById<View>(R.id.drawer_layout)
        verify(fixture).createDrawerToggle(drawerLayout)
        verify(drawerLayout).addDrawerListener(actionBarDrawerToggle)
        verifyNoMoreInteractions(mainActivity)
        verifyNoMoreInteractions(toolbar)
        verifyNoMoreInteractions(configuration)
        verifyNoMoreInteractions(drawerLayout)
        verifyNoMoreInteractions(actionBarDrawerToggle)
    }

    @Test
    fun testCreate() {
        // validate
        verify(actionBarDrawerToggle).syncState()
    }

    @Test
    fun testSyncState() {
        // execute
        fixture.syncState()
        // validate
        verify(actionBarDrawerToggle, times(2)).syncState()
    }

    @Test
    fun testOnConfigurationChanged() {
        // execute
        fixture.onConfigurationChanged(configuration)
        // validate
        verify(actionBarDrawerToggle).onConfigurationChanged(configuration)
        verify(actionBarDrawerToggle).syncState()
    }
}