
package com.sladjan.wifianalyzer.navigation

import android.view.MenuItem
import android.view.View
import androidx.annotation.IdRes
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

interface NavigationMenuControl : NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    fun currentMenuItem(): MenuItem
    fun currentNavigationMenu(): NavigationMenu
    fun currentNavigationMenu(navigationMenu: NavigationMenu)
    fun navigationView(): NavigationView
    fun <T : View?> findViewById(@IdRes id: Int): T
}