
package com.sladjan.wifianalyzer.wifi.predicate

import com.sladjan.wifianalyzer.wifi.model.WiFiDetail
import com.sladjan.wifianalyzer.wifi.model.WiFiIdentifier
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SSIDPredicateTest {
    @Test
    fun testSSIDPredicate() {
        // setup
        val wiFiDetail = WiFiDetail(WiFiIdentifier("ssid", "bssid"), "wpa")
        // execute & validate
        assertTrue(SSIDPredicate("ssid").test(wiFiDetail))
        assertTrue(SSIDPredicate("id").test(wiFiDetail))
        assertTrue(SSIDPredicate("ss").test(wiFiDetail))
        assertTrue(SSIDPredicate("s").test(wiFiDetail))
        assertTrue(SSIDPredicate("").test(wiFiDetail))
        assertFalse(SSIDPredicate("SSID").test(wiFiDetail))
        assertFalse(SSIDPredicate("B").test(wiFiDetail))
    }
}