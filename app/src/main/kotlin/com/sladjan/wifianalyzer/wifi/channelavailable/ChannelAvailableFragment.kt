package com.sladjan.wifianalyzer.wifi.channelavailable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.databinding.ChannelAvailableContentBinding
import com.sladjan.wifianalyzer.wifi.band.WiFiChannelCountry
import com.sladjan.wifianalyzer.wifi.band.WiFiChannelCountry.Companion.find

class ChannelAvailableFragment : ListFragment() {
    private lateinit var channelAvailableAdapter: ChannelAvailableAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = ChannelAvailableContentBinding.inflate(inflater, container, false)
        channelAvailableAdapter = ChannelAvailableAdapter(requireActivity(), channelAvailable())
        listAdapter = channelAvailableAdapter
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        channelAvailableAdapter.clear()
        channelAvailableAdapter.addAll(channelAvailable())
    }

    private fun channelAvailable(): MutableList<WiFiChannelCountry> =
            mutableListOf(find(INSTANCE.settings.countryCode()))

}