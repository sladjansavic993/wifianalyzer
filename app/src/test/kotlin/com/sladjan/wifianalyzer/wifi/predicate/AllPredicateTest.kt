//SS
package com.sladjan.wifianalyzer.wifi.predicate

import com.sladjan.wifianalyzer.wifi.model.WiFiDetail
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AllPredicateTest {

    @Test
    fun testAllPredicateIsTrue() {
        // setup
        val wiFiDetail = WiFiDetail.EMPTY
        val predicates = listOf<Predicate>(TruePredicate(), TruePredicate(), TruePredicate())
        val fixture = AllPredicate(predicates)
        // execute
        val actual = fixture.test(wiFiDetail)
        // validate
        assertTrue(actual)
    }

    @Test
    fun testAllPredicateIsFalse() {
        // setup
        val wiFiDetail = WiFiDetail.EMPTY
        val predicates = listOf<Predicate>(FalsePredicate(), TruePredicate(), FalsePredicate())
        val fixture = AllPredicate(predicates)
        // execute
        val actual = fixture.test(wiFiDetail)
        // validate
        assertFalse(actual)
    }

}