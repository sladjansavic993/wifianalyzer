//SS
package com.sladjan.wifianalyzer.export

import android.content.Intent
import com.nhaarman.mockitokotlin2.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class ExportIntentTest {
    private val intentSend: Intent = mock()
    private val intentChooser: Intent = mock()

    private val fixture = spy(ExportIntent())

    @After
    fun tearDown() {
        verifyNoMoreInteractions(intentSend)
        verifyNoMoreInteractions(intentChooser)
    }

    @Test
    fun testIntent() {
        // setup
        val title = "title"
        val data = "data"
        doReturn(intentSend).whenever(fixture).intentSend()
        doReturn(intentChooser).whenever(fixture).intentChooser(intentSend, title)
        // execute
        val actual = fixture.intent(title, data)
        // validate
        assertEquals(intentChooser, actual)

        verify(intentSend).flags = Intent.FLAG_ACTIVITY_NEW_TASK
        verify(intentSend).type = "text/plain"
        verify(intentSend).putExtra(Intent.EXTRA_TITLE, title)
        verify(intentSend).putExtra(Intent.EXTRA_SUBJECT, title)
        verify(intentSend).putExtra(Intent.EXTRA_TEXT, data)

        verify(fixture).intentSend()
        verify(fixture).intentChooser(intentSend, title)
    }

}