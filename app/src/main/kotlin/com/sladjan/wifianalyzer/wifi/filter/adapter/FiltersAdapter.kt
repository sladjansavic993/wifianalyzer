
package com.sladjan.wifianalyzer.wifi.filter.adapter

import com.sladjan.annotation.OpenClass
import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.navigation.NavigationMenu
import com.sladjan.wifianalyzer.settings.Settings
import java.io.Serializable

@OpenClass
class FiltersAdapter(private val settings: Settings) {
    private lateinit var ssidAdapter: SSIDAdapter
    private lateinit var wiFiBandAdapter: WiFiBandAdapter
    private lateinit var strengthAdapter: StrengthAdapter
    private lateinit var securityAdapter: SecurityAdapter

    fun reload() {
        ssidAdapter = SSIDAdapter(settings.findSSIDs())
        wiFiBandAdapter = WiFiBandAdapter(settings.findWiFiBands())
        strengthAdapter = StrengthAdapter(settings.findStrengths())
        securityAdapter = SecurityAdapter(settings.findSecurities())
    }

    fun reset(): Unit =
            filterAdapters(isAccessPoints()).forEach {
                it.reset()
                it.save(settings)
            }

    fun save(): Unit = filterAdapters(isAccessPoints()).forEach { it.save(settings) }

    fun ssidAdapter(): SSIDAdapter = ssidAdapter

    fun wiFiBandAdapter(): WiFiBandAdapter = wiFiBandAdapter

    fun strengthAdapter(): StrengthAdapter = strengthAdapter

    fun securityAdapter(): SecurityAdapter = securityAdapter

    internal fun isActive(): Boolean = filterAdapters(isAccessPoints()).any { it.isActive() }

    internal fun filterAdapters(accessPoints: Boolean): List<BasicFilterAdapter<out Serializable>> =
            if (accessPoints)
                listOf(ssidAdapter, strengthAdapter, securityAdapter, wiFiBandAdapter)
            else
                listOf(ssidAdapter, strengthAdapter, securityAdapter)

    private fun isAccessPoints(): Boolean =
            NavigationMenu.ACCESS_POINTS == INSTANCE.mainActivity.currentNavigationMenu()

    init {
        ssidAdapter = SSIDAdapter(settings.findSSIDs())
        wiFiBandAdapter = WiFiBandAdapter(settings.findWiFiBands())
        strengthAdapter = StrengthAdapter(settings.findStrengths())
        securityAdapter = SecurityAdapter(settings.findSecurities())
    }
}