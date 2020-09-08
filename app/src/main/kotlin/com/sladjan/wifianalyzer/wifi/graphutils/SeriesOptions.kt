//SS
package com.sladjan.wifianalyzer.wifi.graphutils

import com.jjoe64.graphview.series.BaseSeries
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.series.TitleLineGraphSeries
import com.sladjan.annotation.OpenClass

private fun BaseSeries<GraphDataPoint>.removeSeriesColor(graphColors: GraphColors) = graphColors.addColor(this.color.toLong())

private fun BaseSeries<GraphDataPoint>.highlightConnected(connected: Boolean) {
    val thickness = if (connected) THICKNESS_CONNECTED else THICKNESS_REGULAR
    when (this) {
        is LineGraphSeries<GraphDataPoint> -> this.setThickness(thickness)
        is TitleLineGraphSeries<GraphDataPoint> -> {
            this.thickness = thickness
            this.setTextBold(connected)
        }
    }
}

private fun BaseSeries<GraphDataPoint>.seriesColor(graphColors: GraphColors) {
    val graphColor = graphColors.graphColor()
    this.color = graphColor.primary.toInt()
    when (this) {
        is LineGraphSeries<GraphDataPoint> -> this.backgroundColor = graphColor.background.toInt()
        is TitleLineGraphSeries<GraphDataPoint> -> this.backgroundColor = graphColor.background.toInt()
    }
}

private fun BaseSeries<GraphDataPoint>.drawBackground(drawBackground: Boolean) {
    when (this) {
        is LineGraphSeries<GraphDataPoint> -> this.isDrawBackground = drawBackground
        is TitleLineGraphSeries<GraphDataPoint> -> this.isDrawBackground = drawBackground
    }
}

@OpenClass
class SeriesOptions(private val graphColors: GraphColors = GraphColors()) {

    fun highlightConnected(series: BaseSeries<GraphDataPoint>, connected: Boolean) = series.highlightConnected(connected)

    fun setSeriesColor(series: BaseSeries<GraphDataPoint>) = series.seriesColor(graphColors)

    fun drawBackground(series: BaseSeries<GraphDataPoint>, drawBackground: Boolean) = series.drawBackground(drawBackground)

    fun removeSeriesColor(series: BaseSeries<GraphDataPoint>) = series.removeSeriesColor(graphColors)

}