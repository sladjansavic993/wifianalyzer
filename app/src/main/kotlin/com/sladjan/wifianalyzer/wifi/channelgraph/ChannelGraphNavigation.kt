//SS
package com.sladjan.wifianalyzer.wifi.channelgraph

import android.content.Context
import android.text.Spanned
import android.view.View
import android.widget.Button
import com.sladjan.annotation.OpenClass
import com.sladjan.util.compatColor
import com.sladjan.util.fromHtml
import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.settings.Settings
import com.sladjan.wifianalyzer.wifi.band.WiFiChannelPair
import com.sladjan.wifianalyzer.wifi.band.WiFiChannelsGHZ5
import com.sladjan.wifianalyzer.wifi.model.WiFiData
import com.sladjan.wifianalyzer.wifi.model.WiFiDetail
import com.sladjan.wifianalyzer.wifi.predicate.Predicate
import com.sladjan.wifianalyzer.wifi.predicate.makeOtherPredicate

internal val navigationSet: Map<WiFiChannelPair, Int> = mapOf(
        WiFiChannelsGHZ5.SET1 to R.id.graphNavigationSet1,
        WiFiChannelsGHZ5.SET2 to R.id.graphNavigationSet2,
        WiFiChannelsGHZ5.SET3 to R.id.graphNavigationSet3)

@OpenClass
class ChannelGraphNavigation(private val view: View, private val context: Context) {

    fun update(wiFiData: WiFiData) {
        val settings = INSTANCE.settings
        val wiFiBand = settings.wiFiBand()
        val countryCode = settings.countryCode()
        val wiFiChannels = wiFiBand.wiFiChannels
        val visible = navigationSet
                .filterKeys { wiFiBand.ghz5() && wiFiChannels.channelAvailable(countryCode, it.first.channel) }
                .keys
        updateButtons(wiFiData, visible)
        view.visibility = if (visible.size > 1) View.VISIBLE else View.GONE
    }

    private fun updateButtons(wiFiData: WiFiData, visible: Set<WiFiChannelPair>) {
        if (visible.size > 1) {
            val settings = INSTANCE.settings
            val predicate = predicate(settings)
            val selectedWiFiChannelPair = INSTANCE.configuration.wiFiChannelPair
            val wiFiDetails = wiFiData.wiFiDetails(predicate, settings.sortBy())
            navigationSet.entries.forEach { button(it, visible, selectedWiFiChannelPair, wiFiDetails) }
        }
    }

    fun predicate(settings: Settings): Predicate = makeOtherPredicate(settings)

    private fun button(entry: Map.Entry<WiFiChannelPair, Int>, visible: Set<WiFiChannelPair>, selectedWiFiChannelPair: WiFiChannelPair, wiFiDetails: List<WiFiDetail>) {
        val wiFiChannelPair = entry.key
        val id = entry.value
        val button = view.findViewById<Button>(id)
        if (visible.contains(wiFiChannelPair)) {
            button.visibility = View.VISIBLE
            setSelected(button, wiFiChannelPair == selectedWiFiChannelPair)
            button.text = buttonText(wiFiDetails, wiFiChannelPair)
        } else {
            button.visibility = View.GONE
            setSelected(button, false)
        }
    }

    private fun buttonText(wiFiDetails: List<WiFiDetail>, wiFiChannelPair: WiFiChannelPair): Spanned {
        val activity = if (wiFiDetails.any { wiFiChannelPair.inRange(it) }) "&#9585;&#9586;" else "&#8722"
        return """<strong>${wiFiChannelPair.first.channel} $activity ${wiFiChannelPair.second.channel}</strong>""".fromHtml()
    }

    private fun setSelected(button: Button, selected: Boolean) {
        val color = context.compatColor(if (selected) R.color.selected else R.color.background)
        button.setBackgroundColor(color)
        button.isSelected = selected
    }

    fun initialize() {
        navigationSet.entries.forEach { entry ->
            view.findViewById<View>(entry.value)?.setOnClickListener { onClickListener(entry.key) }
        }
    }

    fun onClickListener(wiFiChannelPair: WiFiChannelPair) {
        val mainContext = INSTANCE
        mainContext.configuration.wiFiChannelPair = wiFiChannelPair
        mainContext.scannerService.update()
    }

}