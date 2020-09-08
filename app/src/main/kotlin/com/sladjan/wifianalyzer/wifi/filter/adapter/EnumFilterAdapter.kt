
package com.sladjan.wifianalyzer.wifi.filter.adapter

import com.sladjan.wifianalyzer.R

abstract class EnumFilterAdapter<T : Enum<*>>(selections: Set<T>, val defaults: Array<T>) : BasicFilterAdapter<T>(selections) {
    override fun isActive(): Boolean = selections.size != defaults.size

    fun toggle(selection: T): Boolean {
        val size = selections.size
        if (selections.contains(selection)) {
            if (size > 1) {
                selections = selections.minus(selection)
            }
        } else {
            selections = selections.plus(selection)
        }
        return size != selections.size
    }

    override fun reset() {
        selections(defaults)
    }

    fun color(selection: T): Int = if (selections.contains(selection)) R.color.selected else R.color.regular

    fun contains(selection: T): Boolean = selections.contains(selection)

}