
package com.sladjan.wifianalyzer.wifi.filter

import android.app.Dialog
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.sladjan.wifianalyzer.wifi.filter.adapter.EnumFilterAdapter

internal abstract class EnumFilter<T : Enum<*>, U : EnumFilterAdapter<T>>(internal val ids: Map<T, Int>, private val filter: U, dialog: Dialog, id: Int) {

    private fun setColor(view: View, value: T) {
        this.filter.color(value).let {
            val color = ContextCompat.getColor(view.context, it)
            when (view) {
                is TextView -> view.setTextColor(color)
                is ImageView -> view.setColorFilter(color)
            }
        }
    }

    init {
        ids.keys.forEach { value -> ids[value]?.let { process(dialog, it, value) } }
        dialog.findViewById<View>(id).visibility = View.VISIBLE
    }

    private fun process(dialog: Dialog, id: Int, value: T) {
        val view = dialog.findViewById<View>(id)
        view.setOnClickListener { onClickListener(value, it) }
        setColor(view, value)
    }

    private fun onClickListener(value: T, view: View) {
        filter.toggle(value)
        setColor(view, value)
    }
}