//SS
package com.sladjan.wifianalyzer.wifi.graphutils

import com.jjoe64.graphview.GraphView
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.sladjan.wifianalyzer.MainContextHelper
import com.sladjan.wifianalyzer.wifi.model.WiFiData
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class GraphAdapterTest {
    private val graphViewNotifier: GraphViewNotifier = mock()
    private val graphView: GraphView = mock()
    private val wiFiData: WiFiData = mock()
    private val fixture = GraphAdapter(listOf(graphViewNotifier))

    @After
    fun tearDown() {
        verifyNoMoreInteractions(graphViewNotifier)
        verifyNoMoreInteractions(graphView)
        verifyNoMoreInteractions(wiFiData)
        MainContextHelper.INSTANCE.restore()
    }

    @Test
    fun testUpdate() {
        // execute
        fixture.update(wiFiData)
        // validate
        verify(graphViewNotifier).update(wiFiData)
    }

    @Test
    fun testGraphViews() {
        // setup
        whenever(graphViewNotifier.graphView()).thenReturn(graphView)
        // execute
        val actual = fixture.graphViews()
        // validate
        assertEquals(1, actual.size)
        assertEquals(graphView, actual[0])
        verify(graphViewNotifier).graphView()
    }
}