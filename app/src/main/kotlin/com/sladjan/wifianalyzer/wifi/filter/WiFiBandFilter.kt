
package com.sladjan.wifianalyzer.wifi.filter

import android.app.Dialog
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.filter.adapter.WiFiBandAdapter

internal class WiFiBandFilter(wiFiBandAdapter: WiFiBandAdapter, dialog: Dialog) :
        EnumFilter<WiFiBand, WiFiBandAdapter>(
                mapOf(
                        WiFiBand.GHZ2 to R.id.filterWifiBand2,
                        WiFiBand.GHZ5 to R.id.filterWifiBand5
                ),
                wiFiBandAdapter,
                dialog,
                R.id.filterWiFiBand
        )