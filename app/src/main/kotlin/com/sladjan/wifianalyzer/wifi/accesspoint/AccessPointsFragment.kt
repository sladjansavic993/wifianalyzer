
package com.sladjan.wifianalyzer.wifi.accesspoint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.sladjan.util.buildVersionP
import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.databinding.AccessPointsContentBinding

class AccessPointsFragment : Fragment(), OnRefreshListener {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var accessPointsAdapter: AccessPointsAdapter
        private set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = AccessPointsContentBinding.inflate(inflater, container, false)
        swipeRefreshLayout = binding.accessPointsRefresh
        swipeRefreshLayout.setOnRefreshListener(this)
        if (buildVersionP()) {
            swipeRefreshLayout.isRefreshing = false
            swipeRefreshLayout.isEnabled = false
        }
        accessPointsAdapter = AccessPointsAdapter()
        binding.accessPointsView.setAdapter(accessPointsAdapter)
        accessPointsAdapter.expandableListView = binding.accessPointsView
        INSTANCE.scannerService.register(accessPointsAdapter)
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
        INSTANCE.scannerService.unregister(accessPointsAdapter)
        super.onDestroy()
    }

}