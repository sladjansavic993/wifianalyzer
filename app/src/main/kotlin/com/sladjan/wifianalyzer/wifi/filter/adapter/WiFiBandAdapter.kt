
package com.sladjan.wifianalyzer.wifi.filter.adapter

import com.sladjan.wifianalyzer.settings.Settings
import com.sladjan.wifianalyzer.wifi.band.WiFiBand

class WiFiBandAdapter(values: Set<WiFiBand>) : EnumFilterAdapter<WiFiBand>(values, WiFiBand.values()) {
    override fun save(settings: Settings): Unit =
            settings.saveWiFiBands(selections)
}