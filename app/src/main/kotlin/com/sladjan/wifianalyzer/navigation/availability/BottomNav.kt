
package com.sladjan.wifianalyzer.navigation.availability

import android.view.View
import com.sladjan.wifianalyzer.R

internal val navigationOptionBottomNavOff: NavigationOption = {
    it.findViewById<View>(R.id.nav_bottom).visibility = View.GONE
}

internal val navigationOptionBottomNavOn: NavigationOption = {
    it.findViewById<View>(R.id.nav_bottom).visibility = View.VISIBLE
}
