
package com.sladjan.wifianalyzer.navigation.items

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.MenuItem
import android.view.View
import com.nhaarman.mockitokotlin2.*
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.navigation.NavigationMenu
import org.junit.After
import org.junit.Assert
import org.junit.Test

class PortAuthorityItemTest {
    private val portAuthority = "com.aaronjwood.portauthority."
    private val portAuthorityFree = portAuthority + "free"
    private val portAuthorityDonate = portAuthority + "donate"

    private val mainActivity: MainActivity = mock()
    private val context: Context = mock()
    private val intent: Intent = mock()
    private val menuItem: MenuItem = mock()
    private val packageManager: PackageManager = mock()
    private val fixture: PortAuthorityItem = spy(PortAuthorityItem())

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mainActivity)
        verifyNoMoreInteractions(menuItem)
        verifyNoMoreInteractions(context)
        verifyNoMoreInteractions(intent)
        verifyNoMoreInteractions(packageManager)
    }

    @Test
    fun testActivateWhenPortAuthorityDonate() {
        // setup
        whenever(mainActivity.applicationContext).thenReturn(context)
        whenever(context.packageManager).thenReturn(packageManager)
        whenever(packageManager.getLaunchIntentForPackage(portAuthorityDonate)).thenReturn(intent)
        // execute
        fixture.activate(mainActivity, menuItem, NavigationMenu.PORT_AUTHORITY)
        // validate
        verify(intent).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        verify(context).startActivity(intent)
        verify(mainActivity).applicationContext
        verify(context).packageManager
        verify(packageManager).getLaunchIntentForPackage(portAuthorityDonate)
    }

    @Test
    fun testActivateWhenPortAuthorityFree() {
        // setup
        whenever(mainActivity.applicationContext).thenReturn(context)
        whenever(context.packageManager).thenReturn(packageManager)
        whenever(packageManager.getLaunchIntentForPackage(portAuthorityDonate)).thenReturn(null)
        whenever(packageManager.getLaunchIntentForPackage(portAuthorityFree)).thenReturn(intent)
        // execute
        fixture.activate(mainActivity, menuItem, NavigationMenu.PORT_AUTHORITY)
        // validate
        verify(intent).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        verify(context).startActivity(intent)
        verify(mainActivity).applicationContext
        verify(context).packageManager
        verify(packageManager).getLaunchIntentForPackage(portAuthorityDonate)
        verify(packageManager).getLaunchIntentForPackage(portAuthorityFree)
    }

    @Test
    fun testActivateWhenNoPortAuthority() {
        // setup
        whenever(mainActivity.applicationContext).thenReturn(context)
        whenever(context.packageManager).thenReturn(packageManager)
        whenever(packageManager.getLaunchIntentForPackage(portAuthorityDonate)).thenReturn(null)
        whenever(packageManager.getLaunchIntentForPackage(portAuthorityFree)).thenReturn(null)
        doReturn(intent).whenever(fixture).redirectToPlayStore()
        // execute
        fixture.activate(mainActivity, menuItem, NavigationMenu.PORT_AUTHORITY)
        // validate
        verify(intent).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        verify(context).startActivity(intent)
        verify(mainActivity).applicationContext
        verify(context).packageManager
        verify(packageManager).getLaunchIntentForPackage(portAuthorityDonate)
        verify(packageManager).getLaunchIntentForPackage(portAuthorityFree)
        verify(fixture).redirectToPlayStore()
    }

    @Test
    fun testRegistered() {
        // setup
        // execute
        val actual: Boolean = fixture.registered
        // validate
        Assert.assertFalse(actual)
    }

    @Test
    fun testVisibility() {
        // setup
        // execute
        val actual: Int = fixture.visibility
        // validate
        Assert.assertEquals(View.GONE, actual)
    }
}