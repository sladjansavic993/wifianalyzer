//SS
package com.sladjan.wifianalyzer.wifi.channelgraph

import android.view.View
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.TitleLineGraphSeries
import com.sladjan.annotation.OpenClass
import com.sladjan.wifianalyzer.MainContext
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.settings.Settings
import com.sladjan.wifianalyzer.settings.ThemeStyle
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.band.WiFiChannelPair
import com.sladjan.wifianalyzer.wifi.band.WiFiChannels
import com.sladjan.wifianalyzer.wifi.graphutils.*
import com.sladjan.wifianalyzer.wifi.model.WiFiData
import com.sladjan.wifianalyzer.wifi.predicate.Predicate
import com.sladjan.wifianalyzer.wifi.predicate.makeOtherPredicate

internal fun WiFiChannelPair.numX(): Int {
    val channelFirst = this.first.channel - WiFiChannels.CHANNEL_OFFSET
    val channelLast = this.second.channel + WiFiChannels.CHANNEL_OFFSET
    return channelLast - channelFirst + 1
}

internal fun WiFiChannelPair.selected(wiFiBand: WiFiBand): Boolean {
    val currentWiFiBand = MainContext.INSTANCE.settings.wiFiBand()
    val currentWiFiChannelPair = MainContext.INSTANCE.configuration.wiFiChannelPair
    return wiFiBand == currentWiFiBand && (WiFiBand.GHZ2 == wiFiBand || this == currentWiFiChannelPair)
}

internal fun makeGraphView(mainContext: MainContext, graphMaximumY: Int, themeStyle: ThemeStyle, wiFiBand: WiFiBand, wiFiChannelPair: WiFiChannelPair): GraphView {
    val resources = mainContext.resources
    return GraphViewBuilder(wiFiChannelPair.numX(), graphMaximumY, themeStyle, true)
            .setLabelFormatter(ChannelAxisLabel(wiFiBand, wiFiChannelPair))
            .setVerticalTitle(resources.getString(R.string.graph_axis_y))
            .setHorizontalTitle(resources.getString(R.string.graph_channel_axis_x))
            .build(mainContext.context)
}

internal fun makeDefaultSeries(frequencyEnd: Int, minX: Int): TitleLineGraphSeries<GraphDataPoint> {
    val dataPoints = arrayOf(
            GraphDataPoint(minX, MIN_Y),
            GraphDataPoint(frequencyEnd + WiFiChannels.FREQUENCY_OFFSET, MIN_Y)
    )
    val series = TitleLineGraphSeries(dataPoints)
    series.color = transparent.primary.toInt()
    series.thickness = THICKNESS_INVISIBLE
    return series
}

internal fun makeGraphViewWrapper(wiFiBand: WiFiBand, wiFiChannelPair: WiFiChannelPair): GraphViewWrapper {
    val settings = MainContext.INSTANCE.settings
    val configuration = MainContext.INSTANCE.configuration
    val themeStyle = settings.themeStyle()
    val graphMaximumY = settings.graphMaximumY()
    val graphView = makeGraphView(MainContext.INSTANCE, graphMaximumY, themeStyle, wiFiBand, wiFiChannelPair)
    val graphViewWrapper = GraphViewWrapper(graphView, settings.channelGraphLegend(), themeStyle)
    configuration.size = graphViewWrapper.size(graphViewWrapper.calculateGraphType())
    val minX = wiFiChannelPair.first.frequency - WiFiChannels.FREQUENCY_OFFSET
    val maxX = minX + graphViewWrapper.viewportCntX * WiFiChannels.FREQUENCY_SPREAD
    graphViewWrapper.setViewport(minX, maxX)
    graphViewWrapper.addSeries(makeDefaultSeries(wiFiChannelPair.second.frequency, minX))
    return graphViewWrapper
}

@OpenClass
internal class ChannelGraphView(private val wiFiBand: WiFiBand,
                                private val wiFiChannelPair: WiFiChannelPair,
                                private var dataManager: DataManager = DataManager(),
                                private var graphViewWrapper: GraphViewWrapper = makeGraphViewWrapper(wiFiBand, wiFiChannelPair))
    : GraphViewNotifier {

    override fun update(wiFiData: WiFiData) {
        val predicate = predicate(MainContext.INSTANCE.settings)
        val wiFiDetails = wiFiData.wiFiDetails(predicate, MainContext.INSTANCE.settings.sortBy())
        val newSeries = dataManager.newSeries(wiFiDetails, wiFiChannelPair)
        dataManager.addSeriesData(graphViewWrapper, newSeries, MainContext.INSTANCE.settings.graphMaximumY())
        graphViewWrapper.removeSeries(newSeries)
        graphViewWrapper.updateLegend(MainContext.INSTANCE.settings.channelGraphLegend())
        graphViewWrapper.visibility(if (selected()) View.VISIBLE else View.GONE)
    }

    fun selected(): Boolean = wiFiChannelPair.selected(wiFiBand)

    fun predicate(settings: Settings): Predicate = makeOtherPredicate(settings)

    override fun graphView(): GraphView {
        return graphViewWrapper.graphView
    }

}