//SS
package com.sladjan.wifianalyzer.wifi.predicate

import com.sladjan.wifianalyzer.wifi.band.WiFiWidth
import com.sladjan.wifianalyzer.wifi.model.Security
import com.sladjan.wifianalyzer.wifi.model.WiFiDetail
import com.sladjan.wifianalyzer.wifi.model.WiFiIdentifier
import com.sladjan.wifianalyzer.wifi.model.WiFiSignal
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SecurityPredicateTest {
    @Test
    fun testSecurityPredicateWithFoundWPAValue() {
        // setup
        val wiFiDetail = wiFiDetail()
        val fixture = SecurityPredicate(Security.WPA)
        // execute
        val actual = fixture.test(wiFiDetail)
        // validate
        assertTrue(actual)
    }

    @Test
    fun testSecurityPredicateWithFoundWEPValue() {
        // setup
        val wiFiDetail = wiFiDetail()
        val fixture = SecurityPredicate(Security.WEP)
        // execute
        val actual = fixture.test(wiFiDetail)
        // validate
        assertTrue(actual)
    }

    @Test
    fun testSecurityPredicateWithFoundNoneValue() {
        // setup
        val wiFiDetail = wiFiDetailWithNoSecurity()
        val fixture = SecurityPredicate(Security.NONE)
        // execute
        val actual = fixture.test(wiFiDetail)
        // validate
        assertTrue(actual)
    }

    @Test
    fun testSecurityPredicateWithNotFoundValue() {
        // setup
        val wiFiDetail = wiFiDetail()
        val fixture = SecurityPredicate(Security.WPA2)
        // execute
        val actual = fixture.test(wiFiDetail)
        // validate
        assertFalse(actual)
    }

    private fun wiFiDetail(): WiFiDetail =
            WiFiDetail(
                    WiFiIdentifier("ssid", "bssid"),
                    "ess-wep-wpa",
                    WiFiSignal(2455, 2455, WiFiWidth.MHZ_20, 1, true))

    private fun wiFiDetailWithNoSecurity(): WiFiDetail =
            WiFiDetail(
                    WiFiIdentifier("ssid", "bssid"),
                    "ess",
                    WiFiSignal(2455, 2455, WiFiWidth.MHZ_20, 1, true))

}