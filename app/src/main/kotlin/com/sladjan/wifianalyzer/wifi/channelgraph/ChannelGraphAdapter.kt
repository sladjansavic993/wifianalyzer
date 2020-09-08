//SS
package com.sladjan.wifianalyzer.wifi.channelgraph

import com.sladjan.annotation.OpenClass
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.graphutils.GraphAdapter
import com.sladjan.wifianalyzer.wifi.model.WiFiData

private fun channelGraphViews(): List<ChannelGraphView> =
        WiFiBand.values().flatMap { wiFiBand ->
            wiFiBand.wiFiChannels.wiFiChannelPairs().map { ChannelGraphView(wiFiBand, it) }
        }

@OpenClass
class ChannelGraphAdapter(private val channelGraphNavigation: ChannelGraphNavigation) : GraphAdapter(channelGraphViews()) {
    override fun update(wiFiData: WiFiData) {
        super.update(wiFiData)
        channelGraphNavigation.update(wiFiData)
    }
}

