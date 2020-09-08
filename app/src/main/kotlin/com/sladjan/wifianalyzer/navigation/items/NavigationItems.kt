//SS
package com.sladjan.wifianalyzer.navigation.items

import android.view.View
import com.sladjan.wifianalyzer.about.AboutFragment
import com.sladjan.wifianalyzer.export.Export
import com.sladjan.wifianalyzer.settings.SettingsFragment
import com.sladjan.wifianalyzer.vendor.VendorFragment
import com.sladjan.wifianalyzer.wifi.accesspoint.AccessPointsFragment
import com.sladjan.wifianalyzer.wifi.channelavailable.ChannelAvailableFragment
import com.sladjan.wifianalyzer.wifi.channelgraph.ChannelGraphFragment
import com.sladjan.wifianalyzer.wifi.channelrating.ChannelRatingFragment
import com.sladjan.wifianalyzer.wifi.timegraph.TimeGraphFragment

val navigationItemAccessPoints: NavigationItem = FragmentItem(AccessPointsFragment())
val navigationItemChannelRating: NavigationItem = FragmentItem(ChannelRatingFragment())
val navigationItemChannelGraph: NavigationItem = FragmentItem(ChannelGraphFragment())
val navigationItemTimeGraph: NavigationItem = FragmentItem(TimeGraphFragment())
val navigationItemExport: NavigationItem = ExportItem(Export())
val navigationItemChannelAvailable: NavigationItem = FragmentItem(ChannelAvailableFragment(), false)
val navigationItemVendors: NavigationItem = FragmentItem(VendorFragment(), false, View.GONE)
val navigationItemSettings: NavigationItem = FragmentItem(SettingsFragment(), false, View.GONE)
val navigationItemAbout: NavigationItem = FragmentItem(AboutFragment(), false, View.GONE)
val navigationItemPortAuthority: NavigationItem = PortAuthorityItem()
