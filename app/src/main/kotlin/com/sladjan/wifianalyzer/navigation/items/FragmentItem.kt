
package com.sladjan.wifianalyzer.navigation.items

import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.navigation.NavigationMenu

internal class FragmentItem(val fragment: Fragment, override val registered: Boolean = true, override val visibility: Int = View.VISIBLE) : NavigationItem {

    override fun activate(mainActivity: MainActivity, menuItem: MenuItem, navigationMenu: NavigationMenu) {
        val fragmentManager: FragmentManager = mainActivity.supportFragmentManager
        if (fragmentManager.isStateSaved) return
        updateMainActivity(mainActivity, menuItem, navigationMenu)
        startFragment(fragmentManager)
    }

    private fun startFragment(fragmentManager: FragmentManager) {
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_fragment, fragment).commit()
    }

    private fun updateMainActivity(mainActivity: MainActivity, menuItem: MenuItem, navigationMenu: NavigationMenu) {
        mainActivity.currentNavigationMenu(navigationMenu)
        mainActivity.title = menuItem.title
        mainActivity.updateActionBar()
        mainActivity.mainConnectionVisibility(visibility)
    }

}