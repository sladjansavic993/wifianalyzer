
package com.sladjan.wifianalyzer.wifi.graphutils

import com.jjoe64.graphview.GraphView
import com.sladjan.wifianalyzer.wifi.model.WiFiData

interface GraphViewNotifier {
    fun graphView(): GraphView
    fun update(wiFiData: WiFiData)
}