
package com.sladjan.wifianalyzer.wifi.filter.adapter

import com.sladjan.wifianalyzer.settings.Settings
import com.sladjan.wifianalyzer.wifi.model.Security

class SecurityAdapter(selections: Set<Security>) : EnumFilterAdapter<Security>(selections, Security.values()) {
    override fun save(settings: Settings) {
        settings.saveSecurities(selections)
    }
}