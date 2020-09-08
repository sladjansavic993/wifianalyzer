//SS
package com.sladjan.wifianalyzer.wifi.channelgraph

import com.jjoe64.graphview.series.TitleLineGraphSeries
import com.sladjan.annotation.OpenClass
import com.sladjan.wifianalyzer.wifi.band.WiFiChannelPair
import com.sladjan.wifianalyzer.wifi.band.WiFiChannels
import com.sladjan.wifianalyzer.wifi.graphutils.GraphDataPoint
import com.sladjan.wifianalyzer.wifi.graphutils.GraphViewWrapper
import com.sladjan.wifianalyzer.wifi.graphutils.MIN_Y
import com.sladjan.wifianalyzer.wifi.model.WiFiDetail

internal fun WiFiChannelPair.inRange(wiFiDetail: WiFiDetail): Boolean {
    val frequency = wiFiDetail.wiFiSignal.centerFrequency
    return frequency >= this.first.frequency && frequency <= this.second.frequency
}

@OpenClass
internal class DataManager {
    fun newSeries(wiFiDetails: List<WiFiDetail>, wiFiChannelPair: WiFiChannelPair): Set<WiFiDetail> =
            wiFiDetails.filter { wiFiChannelPair.inRange(it) }.toSet()

    fun graphDataPoints(wiFiDetail: WiFiDetail, levelMax: Int): Array<GraphDataPoint> {
        val wiFiSignal = wiFiDetail.wiFiSignal
        val frequencyStart = wiFiSignal.frequencyStart()
        val frequencyEnd = wiFiSignal.frequencyEnd()
        val level = wiFiSignal.level.coerceAtMost(levelMax)
        return arrayOf(
                GraphDataPoint(frequencyStart, MIN_Y),
                GraphDataPoint(frequencyStart + WiFiChannels.FREQUENCY_SPREAD, level),
                GraphDataPoint(wiFiSignal.centerFrequency, level),
                GraphDataPoint(frequencyEnd - WiFiChannels.FREQUENCY_SPREAD, level),
                GraphDataPoint(frequencyEnd, MIN_Y)
        )
    }

    fun addSeriesData(graphViewWrapper: GraphViewWrapper, wiFiDetails: Set<WiFiDetail>, levelMax: Int) {
        wiFiDetails.forEach {
            val dataPoints = graphDataPoints(it, levelMax)
            if (graphViewWrapper.newSeries(it)) {
                graphViewWrapper.addSeries(it, TitleLineGraphSeries(dataPoints), true)
            } else {
                graphViewWrapper.updateSeries(it, dataPoints, true)
            }
        }
    }

}