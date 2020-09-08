
package com.sladjan.wifianalyzer.wifi.timegraph

import android.view.View
import com.jjoe64.graphview.GraphView
import com.sladjan.annotation.OpenClass
import com.sladjan.wifianalyzer.MainContext
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.settings.Settings
import com.sladjan.wifianalyzer.settings.ThemeStyle
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.graphutils.GraphViewBuilder
import com.sladjan.wifianalyzer.wifi.graphutils.GraphViewNotifier
import com.sladjan.wifianalyzer.wifi.graphutils.GraphViewWrapper
import com.sladjan.wifianalyzer.wifi.model.WiFiData
import com.sladjan.wifianalyzer.wifi.predicate.Predicate
import com.sladjan.wifianalyzer.wifi.predicate.makeOtherPredicate

private const val NUM_X_TIME = 21

internal fun makeGraphView(mainContext: MainContext, graphMaximumY: Int, themeStyle: ThemeStyle): GraphView =
        GraphViewBuilder(NUM_X_TIME, graphMaximumY, themeStyle, false)
                .setLabelFormatter(TimeAxisLabel())
                .setVerticalTitle(mainContext.resources.getString(R.string.graph_axis_y))
                .setHorizontalTitle(mainContext.resources.getString(R.string.graph_time_axis_x))
                .build(mainContext.context)

internal fun makeGraphViewWrapper(): GraphViewWrapper {
    val settings = MainContext.INSTANCE.settings
    val themeStyle = settings.themeStyle()
    val configuration = MainContext.INSTANCE.configuration
    val graphView = makeGraphView(MainContext.INSTANCE, settings.graphMaximumY(), themeStyle)
    val graphViewWrapper = GraphViewWrapper(graphView, settings.timeGraphLegend(), themeStyle)
    configuration.size = graphViewWrapper.size(graphViewWrapper.calculateGraphType())
    graphViewWrapper.setViewport()
    return graphViewWrapper
}

@OpenClass
internal class TimeGraphView(private val wiFiBand: WiFiBand,
                             private val dataManager: DataManager = DataManager(),
                             private val graphViewWrapper: GraphViewWrapper = makeGraphViewWrapper())
    : GraphViewNotifier {

    override fun update(wiFiData: WiFiData) {
        val predicate = predicate(MainContext.INSTANCE.settings)
        val wiFiDetails = wiFiData.wiFiDetails(predicate, MainContext.INSTANCE.settings.sortBy())
        val newSeries = dataManager.addSeriesData(graphViewWrapper, wiFiDetails, MainContext.INSTANCE.settings.graphMaximumY())
        graphViewWrapper.removeSeries(newSeries)
        graphViewWrapper.updateLegend(MainContext.INSTANCE.settings.timeGraphLegend())
        graphViewWrapper.visibility(if (selected()) View.VISIBLE else View.GONE)
    }

    fun predicate(settings: Settings): Predicate = makeOtherPredicate(settings)

    private fun selected(): Boolean {
        return wiFiBand == MainContext.INSTANCE.settings.wiFiBand()
    }

    override fun graphView(): GraphView {
        return graphViewWrapper.graphView
    }

}