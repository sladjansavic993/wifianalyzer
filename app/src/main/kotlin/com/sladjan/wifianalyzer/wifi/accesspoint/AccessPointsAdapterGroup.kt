
package com.sladjan.wifianalyzer.wifi.accesspoint

import android.widget.ExpandableListView
import com.sladjan.annotation.OpenClass
import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.wifi.model.GroupBy
import com.sladjan.wifianalyzer.wifi.model.WiFiDetail

@OpenClass
class AccessPointsAdapterGroup(val expanded: MutableSet<String> = mutableSetOf(), var groupBy: GroupBy = GroupBy.NONE) {

    fun update(wiFiDetails: List<WiFiDetail>, expandableListView: ExpandableListView?) {
        updateGroupBy()
        if (!groupBy.none) {
            expandableListView?.let { toggle(wiFiDetails, it) }
        }
    }

    fun updateGroupBy() {
        val currentGroupBy = INSTANCE.settings.groupBy()
        if (currentGroupBy != groupBy) {
            expanded.clear()
            groupBy = currentGroupBy
        }
    }

    fun onGroupCollapsed(wiFiDetails: List<WiFiDetail>, groupPosition: Int) {
        if (!groupBy.none) {
            wiFiDetails.getOrNull(groupPosition)?.let {
                if (it.noChildren()) {
                    expanded.remove(groupBy.group(it))
                }
            }
        }
    }

    fun onGroupExpanded(wiFiDetails: List<WiFiDetail>, groupPosition: Int) {
        if (!groupBy.none) {
            wiFiDetails.getOrNull(groupPosition)?.let {
                if (it.noChildren()) {
                    expanded.add(groupBy.group(it))
                }
            }
        }
    }

    private fun toggle(wiFiDetails: List<WiFiDetail>, expandableListView: ExpandableListView) =
            (0 until expandableListView.expandableListAdapter.groupCount).forEach {
                val group = groupBy.group(wiFiDetails.getOrNull(it) ?: WiFiDetail.EMPTY)
                if (expanded.contains(group)) {
                    expandableListView.expandGroup(it)
                } else {
                    expandableListView.collapseGroup(it)
                }
            }

}