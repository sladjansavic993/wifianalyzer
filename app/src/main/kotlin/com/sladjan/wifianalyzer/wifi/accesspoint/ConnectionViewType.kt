
package com.sladjan.wifianalyzer.wifi.accesspoint

import androidx.annotation.LayoutRes
import com.sladjan.wifianalyzer.R

enum class ConnectionViewType(@LayoutRes val layout: Int) {
    COMPLETE(R.layout.access_point_view_complete),
    COMPACT(R.layout.access_point_view_compact),
    HIDE(R.layout.access_point_view_hide);

    val hide: Boolean
        get() = HIDE == this
}
