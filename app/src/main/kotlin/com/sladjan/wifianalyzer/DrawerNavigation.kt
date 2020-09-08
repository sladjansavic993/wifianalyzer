
package com.sladjan.wifianalyzer

import android.content.res.Configuration
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.sladjan.annotation.OpenClass

@OpenClass
class DrawerNavigation(private val mainActivity: MainActivity, private val toolbar: Toolbar) {
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    fun syncState() = actionBarDrawerToggle.syncState()

    fun onConfigurationChanged(newConfig: Configuration?) =
            actionBarDrawerToggle.onConfigurationChanged(newConfig)

    fun create() {
        val drawer = mainActivity.findViewById<DrawerLayout>(R.id.drawer_layout)
        actionBarDrawerToggle = createDrawerToggle(drawer)
        drawer.addDrawerListener(actionBarDrawerToggle)
        syncState()
    }

    fun createDrawerToggle(drawer: DrawerLayout): ActionBarDrawerToggle =
            ActionBarDrawerToggle(mainActivity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

}