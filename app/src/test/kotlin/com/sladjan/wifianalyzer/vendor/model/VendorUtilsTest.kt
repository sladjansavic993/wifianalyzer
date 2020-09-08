//SS
package com.sladjan.wifianalyzer.vendor.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class VendorUtilsTest {

    private val macAddressClean = "0023AB"
    private val macAddressShort = "00:23:AB"
    private val macAddressFull = "00:23:AB:8C:DF:10"

    @Test
    fun testClean() {
        assertTrue("".clean().isEmpty())
        assertEquals(macAddressClean, macAddressFull.clean())
        assertEquals("34AF", "34aF".clean())
        assertEquals("34AF0B", "34aF0B".clean())
        assertEquals("34AA0B", "34:aa:0b".clean())
        assertEquals("34AC0B", "34:ac:0B:A0".clean())
    }

    @Test
    fun testToMacAddress() {
        assertTrue("".toMacAddress().isEmpty())
        assertEquals(macAddressShort, macAddressClean.toMacAddress())
        assertEquals("*34AF*", "34AF".toMacAddress())
        assertEquals("34:AF:0B", "34AF0BAC".toMacAddress())
    }

}