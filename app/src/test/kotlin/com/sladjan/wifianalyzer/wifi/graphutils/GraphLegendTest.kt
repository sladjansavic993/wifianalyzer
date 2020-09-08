
package com.sladjan.wifianalyzer.wifi.graphutils

import com.jjoe64.graphview.LegendRenderer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class GraphLegendTest {
    private val legendRenderer: LegendRenderer = mock()

    @After
    fun tearDown() {
        verifyNoMoreInteractions(legendRenderer)
    }

    @Test
    fun testSortByNumber() {
        assertEquals(3, GraphLegend.values().size)
    }

    @Test
    fun testGetDisplay() {
        assertEquals(GraphLegend.HIDE.legendDisplay, legendDisplayNone)
        assertEquals(GraphLegend.LEFT.legendDisplay, legendDisplayLeft)
        assertEquals(GraphLegend.RIGHT.legendDisplay, legendDisplayRight)
    }

    @Test
    fun testDisplayHide() {
        // execute
        GraphLegend.HIDE.display(legendRenderer)
        // validate
        verify(legendRenderer).isVisible = false
    }

    @Test
    fun testDisplayLeft() {
        // execute
        GraphLegend.LEFT.display(legendRenderer)
        // validate
        verify(legendRenderer).isVisible = true
        verify(legendRenderer).setFixedPosition(0, 0)
    }

    @Test
    fun testDisplayRight() {
        // execute
        GraphLegend.RIGHT.display(legendRenderer)
        // validate
        verify(legendRenderer).isVisible = true
        verify(legendRenderer).align = LegendRenderer.LegendAlign.TOP
    }
}