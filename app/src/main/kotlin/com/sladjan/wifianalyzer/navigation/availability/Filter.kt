
package com.sladjan.wifianalyzer.navigation.availability

import com.sladjan.util.compatColor
import com.sladjan.util.compatTint
import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.R

internal val navigationOptionFilterOff: NavigationOption = {
    it.optionMenu.menu?.let { menu -> menu.findItem(R.id.action_filter).isVisible = false }
}

internal val navigationOptionFilterOn: NavigationOption = {
    it.optionMenu.menu?.let { menu ->
        val menuItem = menu.findItem(R.id.action_filter)
        menuItem.isVisible = true
        val color = if (INSTANCE.filtersAdapter.isActive()) R.color.selected else R.color.regular
        val tint = it.compatColor(color)
        menuItem.icon.compatTint(tint)
    }
}
