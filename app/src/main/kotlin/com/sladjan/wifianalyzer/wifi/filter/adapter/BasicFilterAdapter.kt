
package com.sladjan.wifianalyzer.wifi.filter.adapter

import com.sladjan.annotation.OpenClass
import com.sladjan.wifianalyzer.settings.Settings

@OpenClass
abstract class BasicFilterAdapter<T>(open var selections: Set<T>) {
    fun selections(selections: Array<T>) {
        this.selections = selections.toSet()
    }

    abstract fun isActive(): Boolean
    abstract fun reset()
    abstract fun save(settings: Settings)
}