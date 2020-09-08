
package com.sladjan.wifianalyzer.wifi.predicate

import com.sladjan.wifianalyzer.settings.Settings
import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.model.Security
import com.sladjan.wifianalyzer.wifi.model.Strength
import com.sladjan.wifianalyzer.wifi.model.WiFiDetail

interface Predicate {
    fun test(wiFiDetail: WiFiDetail): Boolean
}

internal class TruePredicate : Predicate {
    override fun test(wiFiDetail: WiFiDetail): Boolean = true
}

internal class FalsePredicate : Predicate {
    override fun test(wiFiDetail: WiFiDetail): Boolean = false
}

internal class AnyPredicate(private val predicates: List<Predicate>) : Predicate {
    override fun test(wiFiDetail: WiFiDetail): Boolean = predicates.any { it.test(wiFiDetail) }
}

internal class AllPredicate(private val predicates: List<Predicate>) : Predicate {
    override fun test(wiFiDetail: WiFiDetail): Boolean = predicates.all { it.test(wiFiDetail) }
}

internal typealias ToPredicate<T> = (T) -> Predicate

internal fun <T : Enum<T>> makePredicate(values: Array<T>, filter: Set<T>, toPredicate: ToPredicate<T>): Predicate =
        if (filter.size >= values.size)
            TruePredicate()
        else
            AnyPredicate(filter.map { toPredicate(it) })

class WiFiBandPredicate(private val wiFiBand: WiFiBand) : Predicate {
    override fun test(wiFiDetail: WiFiDetail): Boolean = wiFiDetail.wiFiSignal.wiFiBand == wiFiBand
}

internal class StrengthPredicate(private val strength: Strength) : Predicate {
    override fun test(wiFiDetail: WiFiDetail): Boolean = wiFiDetail.wiFiSignal.strength() == strength
}

internal class SSIDPredicate(private val ssid: String) : Predicate {
    override fun test(wiFiDetail: WiFiDetail): Boolean = wiFiDetail.wiFiIdentifier.ssid.contains(ssid)
}

internal class SecurityPredicate(private val security: Security) : Predicate {
    override fun test(wiFiDetail: WiFiDetail): Boolean = wiFiDetail.securities().contains(security)
}

private fun makeSSIDPredicate(ssids: Set<String>): Predicate =
        if (ssids.isEmpty())
            TruePredicate()
        else
            AnyPredicate(ssids.map { SSIDPredicate(it) })

private fun makePredicate(settings: Settings, wiFiBands: Set<WiFiBand>): AllPredicate =
        AllPredicate(
                listOf(
                        makeSSIDPredicate(settings.findSSIDs()),
                        makePredicate(WiFiBand.values(), wiFiBands) { WiFiBandPredicate(it) },
                        makePredicate(Strength.values(), settings.findStrengths()) { StrengthPredicate(it) },
                        makePredicate(Security.values(), settings.findSecurities()) { SecurityPredicate(it) }
                )
        )

fun makeAccessPointsPredicate(settings: Settings): Predicate = makePredicate(settings, settings.findWiFiBands())

fun makeOtherPredicate(settings: Settings): Predicate = makePredicate(settings, setOf(settings.wiFiBand()))
