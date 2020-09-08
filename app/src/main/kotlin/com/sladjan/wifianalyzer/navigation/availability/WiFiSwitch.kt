
package com.sladjan.wifianalyzer.navigation.availability

import com.sladjan.util.EMPTY
import com.sladjan.util.compatColor
import com.sladjan.util.fromHtml
import com.sladjan.util.toHtml
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.navigation.options.OptionMenu
import com.sladjan.wifianalyzer.wifi.band.WiFiBand

internal val navigationOptionWiFiSwitchOff: NavigationOption = {
    it.supportActionBar?.let { actionBar ->
        actionBar.subtitle = String.EMPTY
    }
    menu(it.optionMenu, false)
}

internal val navigationOptionWiFiSwitchOn: NavigationOption = {
    actionBarOn(it)
    menu(it.optionMenu, true)
}

private fun menu(optionMenu: OptionMenu, visible: Boolean) {
    optionMenu.menu?.let {
        it.findItem(R.id.action_wifi_band).isVisible = visible
    }
}

private fun actionBarOn(mainActivity: MainActivity) {
    mainActivity.supportActionBar?.let {
        val colorSelected = mainActivity.compatColor(R.color.selected)
        val colorNotSelected = mainActivity.compatColor(R.color.regular)
        val resources = mainActivity.resources
        val wiFiBand2 = resources.getString(WiFiBand.GHZ2.textResource)
        val wiFiBand5 = resources.getString(WiFiBand.GHZ5.textResource)
        val wiFiBand = INSTANCE.settings.wiFiBand()
        val subtitle = makeSubtitle(WiFiBand.GHZ2 == wiFiBand, wiFiBand2, wiFiBand5, colorSelected, colorNotSelected)
        it.subtitle = subtitle.fromHtml()
    }
}

private const val SPACER = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"

internal fun makeSubtitle(wiFiBand2Selected: Boolean, wiFiBand2: String, wiFiBand5: String, colorSelected: Int, colorNotSelected: Int): String =
        if (wiFiBand2Selected) {
            wiFiBand2.toHtml(colorSelected, false) + SPACER + wiFiBand5.toHtml(colorNotSelected, true)
        } else {
            wiFiBand2.toHtml(colorNotSelected, true) + SPACER + wiFiBand5.toHtml(colorSelected, false)
        }

