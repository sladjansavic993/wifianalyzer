
package com.sladjan.wifianalyzer.navigation.availability

import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.R

internal val navigationOptionScannerSwitchOff: NavigationOption = {
    it.optionMenu.menu?.let { menu ->
        menu.findItem(R.id.action_scanner).isVisible = false
    }
}

internal val navigationOptionScannerSwitchOn: NavigationOption = {
    it.optionMenu.menu?.let { menu ->
        val menuItem = menu.findItem(R.id.action_scanner)
        menuItem.isVisible = true
        if (INSTANCE.scannerService.running()) {
            menuItem.setTitle(R.string.scanner_pause)
            menuItem.setIcon(R.drawable.ic_pause)
        } else {
            menuItem.setTitle(R.string.scanner_play)
            menuItem.setIcon(R.drawable.ic_play_arrow)
        }
    }
}
