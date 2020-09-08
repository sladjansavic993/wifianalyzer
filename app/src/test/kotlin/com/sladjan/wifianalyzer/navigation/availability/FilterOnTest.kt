//SS
package com.sladjan.wifianalyzer.navigation.availability

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.sladjan.util.compatColor
import com.sladjan.util.compatTint
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.MainContextHelper.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.navigation.options.OptionMenu
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class FilterOnTest {
    private val mainActivity: MainActivity = mock()
    private val optionMenu: OptionMenu = mock()
    private val menu: Menu = mock()
    private val menuItem: MenuItem = mock()
    private val drawable: Drawable = mock()
    private val filterAdapter = INSTANCE.filterAdapter

    @After
    fun tearDown() {
        INSTANCE.restore()
        verifyNoMoreInteractions(filterAdapter)
        verifyNoMoreInteractions(drawable)
        verifyNoMoreInteractions(menuItem)
        verifyNoMoreInteractions(menu)
        verifyNoMoreInteractions(optionMenu)
        verifyNoMoreInteractions(mainActivity)
    }

    @Test
    fun testNavigationOptionFilterOnWithFilterInactive() {
        // setup
        val colorResult = 200
        whenever(filterAdapter.isActive()).thenReturn(false)
        whenever(mainActivity.compatColor(R.color.regular)).thenReturn(colorResult)
        withMenuItem()
        // execute
        navigationOptionFilterOn(mainActivity)
        // validate
        verifyMenuItem()
        verify(mainActivity).compatColor(R.color.regular)
        verify(drawable).compatTint(colorResult)
    }

    @Test
    fun testNavigationOptionFilterOnWithFilterActive() {
        // setup
        val colorResult = 100
        whenever(filterAdapter.isActive()).thenReturn(true)
        whenever(mainActivity.compatColor(R.color.selected)).thenReturn(colorResult)
        withMenuItem()
        // execute
        navigationOptionFilterOn(mainActivity)
        // validate
        verifyMenuItem()
        verify(mainActivity).compatColor(R.color.selected)
        verify(drawable).compatTint(colorResult)
    }

    @Test
    fun testNavigationOptionFilterOnWithNoMenuDoesNotSetVisibleTrue() {
        // setup
        whenever(mainActivity.optionMenu).thenReturn(optionMenu)
        whenever(optionMenu.menu).thenReturn(null)
        // execute
        navigationOptionFilterOn(mainActivity)
        // validate
        verify(mainActivity).optionMenu
        verify(optionMenu).menu
    }

    private fun verifyMenuItem() {
        verify(mainActivity).optionMenu
        verify(optionMenu).menu
        verify(menu).findItem(R.id.action_filter)
        verify(menuItem).icon
        verify(filterAdapter).isActive()
        verify(menuItem).isVisible = true
    }

    private fun withMenuItem() {
        whenever(mainActivity.optionMenu).thenReturn(optionMenu)
        whenever(optionMenu.menu).thenReturn(menu)
        whenever(menu.findItem(R.id.action_filter)).thenReturn(menuItem)
        whenever(menuItem.icon).thenReturn(drawable)
    }
}