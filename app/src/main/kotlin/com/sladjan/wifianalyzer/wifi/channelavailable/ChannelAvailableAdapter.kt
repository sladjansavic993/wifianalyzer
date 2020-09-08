//SS
package com.sladjan.wifianalyzer.wifi.channelavailable

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.databinding.ChannelAvailableDetailsBinding
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.band.WiFiChannelCountry

internal class ChannelAvailableAdapter(context: Context, wiFiChannelCountries: List<WiFiChannelCountry>) :
        ArrayAdapter<WiFiChannelCountry>(context, R.layout.channel_available_details, wiFiChannelCountries) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val binding = view?.let { ChannelAvailableAdapterBinding(it) } ?: ChannelAvailableAdapterBinding(create(parent))
        val rootView = binding.root
        getItem(position)?.let {
            val resources = rootView.resources
            val currentLocale = INSTANCE.settings.languageLocale()
            binding.channelAvailableCountry.text = "${it.countryCode()} - ${it.countryName(currentLocale)}"
            binding.channelAvailableTitleGhz2.text = "${resources.getString(WiFiBand.GHZ2.textResource)} : "
            binding.channelAvailableGhz2.text = it.channelsGHZ2().joinToString(",")
            binding.channelAvailableTitleGhz5.text = "${resources.getString(WiFiBand.GHZ5.textResource)} : "
            binding.channelAvailableGhz5.text = it.channelsGHZ5().joinToString(",")
        }
        return rootView
    }

    private fun create(parent: ViewGroup): ChannelAvailableDetailsBinding =
            ChannelAvailableDetailsBinding.inflate(INSTANCE.layoutInflater, parent, false)

}