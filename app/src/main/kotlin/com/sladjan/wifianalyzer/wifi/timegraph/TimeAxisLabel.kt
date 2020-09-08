
package com.sladjan.wifianalyzer.wifi.timegraph

import com.jjoe64.graphview.LabelFormatter
import com.jjoe64.graphview.Viewport
import com.sladjan.util.EMPTY
import com.sladjan.wifianalyzer.wifi.graphutils.MAX_Y
import com.sladjan.wifianalyzer.wifi.graphutils.MIN_Y

internal class TimeAxisLabel : LabelFormatter {
    override fun formatLabel(value: Double, isValueX: Boolean): String {
        val valueAsInt = (value + if (value < 0) -0.5 else 0.5).toInt()
        return when {
            isValueX -> {
                if (valueAsInt > 0 && valueAsInt % 2 == 0) {
                    valueAsInt.toString()
                } else {
                    String.EMPTY
                }
            }
            valueAsInt in (MIN_Y + 1)..MAX_Y -> valueAsInt.toString()
            else -> String.EMPTY
        }
    }

    override fun setViewport(viewport: Viewport) {
        // Do nothing
    }
}