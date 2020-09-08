
package com.sladjan.wifianalyzer.wifi.predicate

import org.junit.Assert.assertTrue
import org.junit.Test

class MakePredicateTest {

    @Test
    fun testMakePredicateExpectsTruePredicate() {
        // setup
        val toPredicate: ToPredicate<TestObject> = { TruePredicate() }
        val filters: Set<TestObject> = TestObject.values().toSet()
        // execute
        val actual: Predicate = makePredicate(TestObject.values(), filters, toPredicate)
        // validate
        assertTrue(actual is TruePredicate)
    }

    @Test
    fun testMakePredicateExpectsAnyPredicate() {
        // setup
        val toPredicate: ToPredicate<TestObject> = { TruePredicate() }
        val filters: Set<TestObject> = setOf(TestObject.VALUE1, TestObject.VALUE3)
        // execute
        val actual: Predicate = makePredicate(TestObject.values(), filters, toPredicate)
        // validate
        assertTrue(actual is AnyPredicate)
    }

}