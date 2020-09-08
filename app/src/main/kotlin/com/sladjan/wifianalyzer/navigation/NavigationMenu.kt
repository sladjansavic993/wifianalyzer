
package com.sladjan.wifianalyzer.navigation

import android.view.MenuItem
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.navigation.availability.*
import com.sladjan.wifianalyzer.navigation.items.*

enum class NavigationMenu(val icon: Int,
                          val title: Int,
                          val navigationItem: NavigationItem,
                          val navigationOptions: List<NavigationOption> = navigationOptionOff) {
    ACCESS_POINTS(R.drawable.ic_network_wifi, R.string.action_access_points, navigationItemAccessPoints, navigationOptionAp),
    CHANNEL_RATING(R.drawable.ic_wifi_tethering, R.string.action_channel_rating, navigationItemChannelRating, navigationOptionRating),
    CHANNEL_GRAPH(R.drawable.ic_insert_chart, R.string.action_channel_graph, navigationItemChannelGraph, navigationOptionOther),
    TIME_GRAPH(R.drawable.ic_show_chart, R.string.action_time_graph, navigationItemTimeGraph, navigationOptionOther),
    EXPORT(R.drawable.ic_import_export, R.string.action_export, navigationItemExport),
    CHANNEL_AVAILABLE(R.drawable.ic_location_on, R.string.action_channel_available, navigationItemChannelAvailable),
    VENDORS(R.drawable.ic_list_grey, R.string.action_vendors, navigationItemVendors),
    //PORT_AUTHORITY(R.drawable.ic_lan, R.string.action_port_authority, navigationItemPortAuthority),
    SETTINGS(R.drawable.ic_settings, R.string.action_settings, navigationItemSettings),
    ABOUT(R.drawable.ic_info_outline, R.string.action_about, navigationItemAbout);

    fun activateNavigationMenu(mainActivity: MainActivity, menuItem: MenuItem): Unit =
            navigationItem.activate(mainActivity, menuItem, this)

    fun activateOptions(mainActivity: MainActivity): Unit = navigationOptions.forEach { it(mainActivity) }

    fun wiFiBandSwitchable(): Boolean = navigationOptions.contains(navigationOptionWiFiSwitchOn)

    fun registered(): Boolean = navigationItem.registered

}