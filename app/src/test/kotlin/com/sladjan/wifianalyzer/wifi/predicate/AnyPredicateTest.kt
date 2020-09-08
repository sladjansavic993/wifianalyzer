//SS
package com.sladjan.wifianalyzer.wifi.predicate

import com.sladjan.wifianalyzer.wifi.model.WiFiDetail
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AmyPredicateTest {

    @Test
    fun testAnyPredicateIsTrue() {
        // setup
        val wiFiDetail = WiFiDetail.EMPTY
        val predicates = listOf(FalsePredicate(), TruePredicate(), FalsePredicate())
        val fixture = AnyPredicate(predicates)
        // execute
        val actual = fixture.test(wiFiDetail)
        // validate
        assertTrue(actual)
    }

    @Test
    fun testAnyPredicateIsFalse() {
        // setup
        val wiFiDetail = WiFiDetail.EMPTY
        val predicates = listOf(FalsePredicate(), FalsePredicate(), FalsePredicate())
        val fixture = AnyPredicate(predicates)
        // execute
        val actual = fixture.test(wiFiDetail)
        // validate
        assertFalse(actual)
    }

}