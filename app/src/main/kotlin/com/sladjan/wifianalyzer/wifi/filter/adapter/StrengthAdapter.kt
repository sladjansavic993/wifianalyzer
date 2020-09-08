
package com.sladjan.wifianalyzer.wifi.filter.adapter

import com.sladjan.wifianalyzer.settings.Settings
import com.sladjan.wifianalyzer.wifi.model.Strength

class StrengthAdapter(selections: Set<Strength>) : EnumFilterAdapter<Strength>(selections, Strength.values()) {
    override fun color(selection: Strength): Int =
            if (selections.contains(selection)) selection.colorResource() else Strength.colorResourceDefault

    override fun save(settings: Settings): Unit =
            settings.saveStrengths(selections)
}