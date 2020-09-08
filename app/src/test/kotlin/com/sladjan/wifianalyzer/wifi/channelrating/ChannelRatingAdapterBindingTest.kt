//SS
package com.sladjan.wifianalyzer.wifi.channelrating

import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sladjan.wifianalyzer.R
import org.junit.Assert.assertEquals
import org.junit.Test

class ChannelRatingAdapterBindingTest {

    @Test
    fun testChannelRatingAdapterBinding() {
        // setup
        val view: View = mock()
        val channelNumber: TextView = mock()
        val accessPointCount: TextView = mock()
        val channelRating: RatingBar = mock()
        whenever(view.findViewById<TextView>(R.id.channelNumber)).thenReturn(channelNumber)
        whenever(view.findViewById<TextView>(R.id.accessPointCount)).thenReturn(accessPointCount)
        whenever(view.findViewById<RatingBar>(R.id.channelRating)).thenReturn(channelRating)
        // execute
        val fixture = ChannelRatingAdapterBinding(view)
        // validate
        assertEquals(view, fixture.root)
        assertEquals(channelNumber, fixture.channelNumber)
        assertEquals(accessPointCount, fixture.accessPointCount)
        assertEquals(channelRating, fixture.channelRating)
        verify(view).findViewById<View>(R.id.channelNumber)
        verify(view).findViewById<View>(R.id.accessPointCount)
        verify(view).findViewById<View>(R.id.channelRating)
    }

}