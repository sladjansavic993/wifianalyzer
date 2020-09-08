//SS
package com.sladjan.wifianalyzer.navigation.items

import android.view.MenuItem
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.navigation.NavigationMenu

interface NavigationItem {
    fun activate(mainActivity: MainActivity, menuItem: MenuItem, navigationMenu: NavigationMenu)
    val registered: Boolean
        get() = false
    val visibility: Int
        get() = android.view.View.GONE
}