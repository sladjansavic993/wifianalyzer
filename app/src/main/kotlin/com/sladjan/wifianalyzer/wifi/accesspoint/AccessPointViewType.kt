//SS
package com.sladjan.wifianalyzer.wifi.accesspoint

import androidx.annotation.LayoutRes
import com.sladjan.wifianalyzer.R

enum class AccessPointViewType(@LayoutRes val layout: Int) {
    COMPLETE(R.layout.access_point_view_complete),
    COMPACT(R.layout.access_point_view_compact);
}