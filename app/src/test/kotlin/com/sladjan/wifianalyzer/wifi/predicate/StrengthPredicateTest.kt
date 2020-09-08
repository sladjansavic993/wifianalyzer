
package com.sladjan.wifianalyzer.wifi.predicate

import com.sladjan.wifianalyzer.wifi.band.WiFiWidth
import com.sladjan.wifianalyzer.wifi.model.Strength
import com.sladjan.wifianalyzer.wifi.model.WiFiDetail
import com.sladjan.wifianalyzer.wifi.model.WiFiIdentifier
import com.sladjan.wifianalyzer.wifi.model.WiFiSignal
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class StrengthPredicateTest {
    @Test
    fun testStrengthPredicate() {
        // setup
        val wiFiDetail = makeWiFiDetail(-60)
        // execute & validate
        assertTrue(StrengthPredicate(Strength.THREE).test(wiFiDetail))
        assertFalse(StrengthPredicate(Strength.FOUR).test(wiFiDetail))
    }

    private fun makeWiFiDetail(level: Int): WiFiDetail =
            WiFiDetail(
                    WiFiIdentifier("ssid", "bssid"),
                    "wpa",
                    WiFiSignal(2445, 2445, WiFiWidth.MHZ_20, level, true))
}