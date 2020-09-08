//SS

package com.sladjan.wifianalyzer.wifi.graphutils

import com.jjoe64.graphview.series.DataPoint

class GraphDataPoint(x: Double, y: Double) : DataPoint(x, y) {

    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as DataPoint
        return x == other.x && y == other.y
    }

    override fun hashCode(): Int = 31 * x.hashCode() + y.hashCode()
}