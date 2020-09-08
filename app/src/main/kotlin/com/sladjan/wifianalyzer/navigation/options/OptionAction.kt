
package com.sladjan.wifianalyzer.navigation.options

import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.wifi.filter.Filter

typealias Action = () -> Unit

internal val noAction: Action = { }

internal val filterAction: Action = { Filter.build().show() }

internal val scannerAction: Action = { INSTANCE.scannerService.toggle() }

internal val wiFiBandAction: Action = { INSTANCE.settings.toggleWiFiBand() }

internal enum class OptionAction(val key: Int, val action: Action) {
    NO_ACTION(-1, noAction),
    SCANNER(R.id.action_scanner, scannerAction),
    FILTER(R.id.action_filter, filterAction),
    WIFI_BAND(R.id.action_wifi_band, wiFiBandAction);

    companion object {
        fun findOptionAction(key: Int): OptionAction {
            for (value in values()) {
                if (value.key == key) return value
            }
            return NO_ACTION
        }
    }

}