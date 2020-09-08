
package com.sladjan.wifianalyzer.wifi.band

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class WiFiChannelCountryGHZ5Test {
    private val channelsSet1: SortedSet<Int> = sortedSetOf(36, 40, 44, 48, 52, 56, 60, 64)
    private val channelsSet2: SortedSet<Int> = sortedSetOf(100, 104, 108, 112, 116, 120, 124, 128, 132, 136, 140, 144)
    private val channelsSet3: SortedSet<Int> = sortedSetOf(149, 153, 157, 161, 165)
    private val fixture = WiFiChannelCountryGHZ5()

    @Test
    fun testChannelsAustraliaCanada() {
        val exclude: SortedSet<Int> = sortedSetOf(120, 124, 128)
        val expectedSize = channelsSet1.size + channelsSet2.size + channelsSet3.size - exclude.size
        listOf("AU", "CA").forEach {
            val actual: Set<Int> = fixture.findChannels(it)
            assertEquals(expectedSize, actual.size)
            assertTrue(actual.containsAll(channelsSet1))
            assertTrue(actual.containsAll(channelsSet3))
            assertFalse(actual.containsAll(exclude))
        }
    }

    @Test
    fun testChannelsChinaSouthKorea() {
        val expectedSize = channelsSet1.size + channelsSet3.size
        listOf("CN", "KR").forEach {
            val actual: Set<Int> = fixture.findChannels(it)
            assertEquals(expectedSize, actual.size)
            assertTrue(actual.containsAll(channelsSet1))
            assertTrue(actual.containsAll(channelsSet3))
            assertFalse(actual.containsAll(channelsSet2))
        }
    }

    @Test
    fun testChannelsJapanTurkeySouthAfrica() {
        val expectedSize = channelsSet1.size + channelsSet2.size
        listOf("JP", "TR", "ZA").forEach {
            val actual: Set<Int> = fixture.findChannels(it)
            assertEquals(expectedSize, actual.size)
            assertTrue(actual.containsAll(channelsSet1))
            assertTrue(actual.containsAll(channelsSet2))
            assertFalse(actual.containsAll(channelsSet3))
        }
    }

    @Test
    fun testChannelsIsrael() {
        val expectedSize = channelsSet1.size
        val actual: Set<Int> = fixture.findChannels("IL")
        assertEquals(expectedSize, actual.size)
        assertTrue(actual.containsAll(channelsSet1))
        assertFalse(actual.containsAll(channelsSet2))
        assertFalse(actual.containsAll(channelsSet3))
    }

    @Test
    fun testChannelsOther() {
        val expectedSize = channelsSet1.size + channelsSet2.size + channelsSet3.size
        listOf("US", "RU", "XYZ").forEach {
            val actual: Set<Int> = fixture.findChannels(it)
            assertEquals(expectedSize, actual.size)
            assertTrue(actual.containsAll(channelsSet1))
            assertTrue(actual.containsAll(channelsSet2))
            assertTrue(actual.containsAll(channelsSet3))
        }
    }

}