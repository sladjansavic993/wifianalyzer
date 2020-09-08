
package com.sladjan.wifianalyzer.wifi.filter.adapter

import com.sladjan.wifianalyzer.settings.Settings

class SSIDAdapter(selections: Set<String>) : BasicFilterAdapter<String>(selections) {
    override var selections: Set<String>
        get() = super.selections
        set(values) {
            super.selections = values.filter { it.isNotBlank() }.toSet()
        }

    override fun isActive(): Boolean = selections.isNotEmpty()

    override fun reset() {
        selections = setOf()
    }

    override fun save(settings: Settings): Unit =
            settings.saveSSIDs(selections)

}