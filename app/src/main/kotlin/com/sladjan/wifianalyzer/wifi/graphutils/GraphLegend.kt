//SS
package com.sladjan.wifianalyzer.wifi.graphutils

import com.jjoe64.graphview.LegendRenderer

internal typealias LegendDisplay = (legendRenderer: LegendRenderer) -> Unit

internal val legendDisplayNone: LegendDisplay = { it.isVisible = false }

internal val legendDisplayLeft: LegendDisplay = {
    it.isVisible = true
    it.setFixedPosition(0, 0)
}

internal val legendDisplayRight: LegendDisplay = {
    it.isVisible = true
    it.align = LegendRenderer.LegendAlign.TOP
}

enum class GraphLegend(val legendDisplay: LegendDisplay) {
    LEFT(legendDisplayLeft),
    RIGHT(legendDisplayRight),
    HIDE(legendDisplayNone);

    fun display(legendRenderer: LegendRenderer) {
        legendDisplay(legendRenderer)
    }

}