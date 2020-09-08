
package com.sladjan.wifianalyzer.wifi.filter

import android.app.Dialog
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.sladjan.util.SPACE_SEPARATOR
import com.sladjan.util.specialTrim
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.wifi.filter.adapter.SSIDAdapter

internal class SSIDFilter(ssidAdapter: SSIDAdapter, dialog: Dialog) {
    internal class OnChange(private val ssidAdapter: SSIDAdapter) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // Do nothing
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // Do nothing
        }

        override fun afterTextChanged(s: Editable) {
            ssidAdapter.selections = "$s".specialTrim().split(String.SPACE_SEPARATOR).toSet()
        }
    }

    init {
        val value: String = ssidAdapter.selections.toTypedArray().joinToString(separator = String.SPACE_SEPARATOR).specialTrim()
        val editText: EditText = dialog.findViewById(R.id.filterSSIDtext)
        editText.setText(value)
        editText.addTextChangedListener(OnChange(ssidAdapter))
        dialog.findViewById<View>(R.id.filterSSID).visibility = View.VISIBLE
    }
}