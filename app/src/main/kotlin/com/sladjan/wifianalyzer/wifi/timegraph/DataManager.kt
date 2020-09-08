//SS
package com.sladjan.wifianalyzer.wifi.timegraph

import com.jjoe64.graphview.series.LineGraphSeries
import com.sladjan.annotation.OpenClass
import com.sladjan.wifianalyzer.wifi.graphutils.*
import com.sladjan.wifianalyzer.wifi.model.WiFiDetail

@OpenClass
internal class DataManager(private val timeGraphCache: TimeGraphCache = TimeGraphCache()) {
    var scanCount: Int = 0
    var xValue = 0

    fun addSeriesData(graphViewWrapper: GraphViewWrapper, wiFiDetails: List<WiFiDetail>, levelMax: Int): Set<WiFiDetail> {
        val inOrder: Set<WiFiDetail> = wiFiDetails.toSet()
        inOrder.forEach { addData(graphViewWrapper, it, levelMax) }
        adjustData(graphViewWrapper, inOrder)
        xValue++
        if (scanCount < MAX_SCAN_COUNT) {
            scanCount++
        }
        if (scanCount == 2) {
            graphViewWrapper.setHorizontalLabelsVisible(true)
        }
        return newSeries(inOrder)
    }

    fun adjustData(graphViewWrapper: GraphViewWrapper, wiFiDetails: Set<WiFiDetail>) {
        graphViewWrapper.differenceSeries(wiFiDetails).forEach {
            val dataPoint = GraphDataPoint(xValue, MIN_Y + MIN_Y_OFFSET)
            val drawBackground = it.wiFiAdditional.wiFiConnection.connected()
            graphViewWrapper.appendToSeries(it, dataPoint, scanCount, drawBackground)
            timeGraphCache.add(it)
        }
        timeGraphCache.clear()
    }

    fun newSeries(wiFiDetails: Set<WiFiDetail>): Set<WiFiDetail> = wiFiDetails.plus(timeGraphCache.active())

    fun addData(graphViewWrapper: GraphViewWrapper, wiFiDetail: WiFiDetail, levelMax: Int) {
        val drawBackground = wiFiDetail.wiFiAdditional.wiFiConnection.connected()
        val level = wiFiDetail.wiFiSignal.level.coerceAtMost(levelMax)
        if (graphViewWrapper.newSeries(wiFiDetail)) {
            val dataPoint = GraphDataPoint(xValue, (if (scanCount > 0) MIN_Y + MIN_Y_OFFSET else level))
            val series = LineGraphSeries(arrayOf(dataPoint))
            graphViewWrapper.addSeries(wiFiDetail, series, drawBackground)
        } else {
            val dataPoint = GraphDataPoint(xValue, level)
            graphViewWrapper.appendToSeries(wiFiDetail, dataPoint, scanCount, drawBackground)
        }
        timeGraphCache.reset(wiFiDetail)
    }

}