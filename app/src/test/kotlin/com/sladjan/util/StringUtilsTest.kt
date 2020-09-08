//SS
package com.sladjan.util

import android.os.Build
import android.text.Spanned
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class StringUtilsTest {

    @Test
    fun testSpecialTrim() {
        // setup
        val expected = "ABS ADF"
        val value = "    ABS    ADF    "
        // execute
        val actual: String = value.specialTrim()
        // verify
        assertEquals(expected, actual)
    }

    @Test
    fun testToHtmlSmall() {
        // setup
        val color = 10
        val text = "ThisIsText"
        val expected = "<font color='$color'><small>$text</small></font>"
        // execute
        val actual: String = text.toHtml(color, true)
        // validate
        assertEquals(expected, actual)
    }

    @Test
    fun testToHtml() {
        // setup
        val color = 10
        val text = "ThisIsText"
        val expected = "<font color='$color'><strong>$text</strong></font>"
        // execute
        val actual: String = text.toHtml(color, false)
        // validate
        assertEquals(expected, actual)
    }

    @Test
    fun testFromHtml() {
        // setup
        val expected = "ThisIsText"
        val text = "<font color='20'><small>$expected</small></font>"
        // execute
        val actual: Spanned = text.fromHtml()
        // verify
        assertEquals(expected, actual.toString())
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.M])
    fun testFromHtmlLegacy() {
        // setup
        val expected = "ThisIsText"
        val text = "<font color='20'><small>$expected</small></font>"
        // execute
        val actual: Spanned = text.fromHtml()
        // verify
        assertEquals(expected, actual.toString())
    }

}