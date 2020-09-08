//SS
package com.sladjan.wifianalyzer.wifi.channelrating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.sladjan.util.buildVersionP
import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.databinding.ChannelRatingContentBinding

class ChannelRatingFragment : Fragment(), OnRefreshListener {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var channelRatingAdapter: ChannelRatingAdapter
        private set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ChannelRatingContentBinding = ChannelRatingContentBinding.inflate(inflater, container, false)
        swipeRefreshLayout = binding.channelRatingRefresh
        swipeRefreshLayout.setOnRefreshListener(this)
        if (buildVersionP()) {
            swipeRefreshLayout.isRefreshing = false
            swipeRefreshLayout.isEnabled = false
        }
        val bestChannels: TextView = binding.channelRatingBest.channelRatingBestChannels
        channelRatingAdapter = ChannelRatingAdapter(requireActivity(), bestChannels)
        val listView: ListView = binding.channelRatingRefresh.findViewById(R.id.channelRatingView)
        listView.adapter = channelRatingAdapter
        INSTANCE.scannerService.register(channelRatingAdapter)
        return binding.root
    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = true
        INSTANCE.scannerService.update()
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    override fun onDestroy() {
        INSTANCE.scannerService.unregister(channelRatingAdapter)
        super.onDestroy()
    }

}