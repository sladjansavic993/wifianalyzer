//SS
package com.sladjan.util

import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.InputStream

class FileUtilsTest {

    private val resources: Resources = mock()

    @After
    fun tearDown() {
        verifyNoMoreInteractions(resources)
    }

    @Test
    fun testReadFile() {
        // setup
        val id = 11
        val expected = "Line-1\nLine-2\n"
        val inputStream: InputStream = ByteArrayInputStream(expected.toByteArray())
        whenever(resources.openRawResource(id)).thenReturn(inputStream)
        // execute
        val actual: String = readFile(resources, id)
        // validate
        assertEquals(expected, actual)
        verify(resources).openRawResource(id)
    }

    @Test
    fun testReadFileHandleException() {
        // setup
        val id = 11
        whenever(resources.openRawResource(id)).thenThrow(NotFoundException::class.java)
        // execute
        val actual: String = readFile(resources, id)
        // validate
        assertTrue(actual.isEmpty())
        verify(resources).openRawResource(id)
    }
}