//SS
package com.sladjan.wifianalyzer.wifi.timegraph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.sladjan.util.buildVersionP
import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.databinding.GraphContentBinding
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.graphutils.GraphAdapter

private fun timeGraphViews(): List<TimeGraphView> = WiFiBand.values().map { TimeGraphView(it) }

class TimeGraphAdapter : GraphAdapter(timeGraphViews())

class TimeGraphFragment : Fragment(), OnRefreshListener {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var timeGraphAdapter: TimeGraphAdapter
        private set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = GraphContentBinding.inflate(inflater, container, false)
        swipeRefreshLayout = binding.graphRefresh
        swipeRefreshLayout.setOnRefreshListener(this)
        if (buildVersionP()) {
            swipeRefreshLayout.isRefreshing = false
            swipeRefreshLayout.isEnabled = false
        }
        timeGraphAdapter = TimeGraphAdapter()
        timeGraphAdapter.graphViews().forEach { binding.graphFlipper.addView(it) }
        INSTANCE.scannerService.register(timeGraphAdapter)
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
        INSTANCE.scannerService.unregister(timeGraphAdapter)
        super.onDestroy()
    }

}