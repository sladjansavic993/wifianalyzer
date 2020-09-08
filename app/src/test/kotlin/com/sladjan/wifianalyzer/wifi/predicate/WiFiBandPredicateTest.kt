//SS
package com.sladjan.wifianalyzer.wifi.predicate

import com.sladjan.wifianalyzer.wifi.band.WiFiBand
import com.sladjan.wifianalyzer.wifi.band.WiFiWidth
import com.sladjan.wifianalyzer.wifi.model.WiFiDetail
import com.sladjan.wifianalyzer.wifi.model.WiFiIdentifier
import com.sladjan.wifianalyzer.wifi.model.WiFiSignal
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class WiFiBandPredicateTest {
    @Test
    fun testWiFiBandPredicateWith2GHzFrequency() {
        // setup
        val wiFiDetail = makeWiFiDetail(2455)
        // execute & validate
        assertTrue(WiFiBandPredicate(WiFiBand.GHZ2).test(wiFiDetail))
        assertFalse(WiFiBandPredicate(WiFiBand.GHZ5).test(wiFiDetail))
    }

    @Test
    fun testWiFiBandPredicateWith5GHzFrequency() {
        // setup
        val wiFiDetail = makeWiFiDetail(5455)
        // execute & validate
        assertFalse(WiFiBandPredicate(WiFiBand.GHZ2).test(wiFiDetail))
        assertTrue(WiFiBandPredicate(WiFiBand.GHZ5).test(wiFiDetail))
    }

    private fun makeWiFiDetail(frequency: Int): WiFiDetail =
            WiFiDetail(
                    WiFiIdentifier("ssid", "bssid"),
                    "wpa",
                    WiFiSignal(frequency, frequency, WiFiWidth.MHZ_20, 1, true))

}