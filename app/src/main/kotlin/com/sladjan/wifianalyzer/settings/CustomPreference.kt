//SS
package com.sladjan.wifianalyzer.settings

import android.content.Context
import android.util.AttributeSet
import androidx.preference.ListPreference

abstract class CustomPreference(context: Context, attrs: AttributeSet, values: List<Data>, defaultValue: String) : ListPreference(context, attrs) {
    init {
        this.entries = names(values)
        this.entryValues = codes(values)
        setDefaultValue(defaultValue)
    }

    private fun codes(data: List<Data>): Array<CharSequence> {
        return data.map { it.code }.toTypedArray()
    }

    private fun names(data: List<Data>): Array<CharSequence> {
        return data.map { it.name }.toTypedArray()
    }

}