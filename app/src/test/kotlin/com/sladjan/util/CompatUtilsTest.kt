//SS
package com.sladjan.util

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.sladjan.wifianalyzer.R
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.util.*

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class CompatUtilsTest {

    private val context: Context = mock()
    private val contextWrapper: ContextWrapper = mock()
    private val resources: Resources = mock()
    private val configuration: Configuration = mock()
    private val displayMetrics: DisplayMetrics = mock()
    private val drawable: Drawable = mock()

    private lateinit var newLocale: Locale

    @Before
    fun setUp() {
        newLocale = Locale.US
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(context)
        verifyNoMoreInteractions(contextWrapper)
        verifyNoMoreInteractions(resources)
        verifyNoMoreInteractions(configuration)
        verifyNoMoreInteractions(displayMetrics)
        verifyNoMoreInteractions(drawable)
    }

    @Test
    fun testCreateContext() {
        // setup
        whenever(context.resources).thenReturn(resources)
        whenever(resources.configuration).thenReturn(configuration)
        whenever(context.createConfigurationContext(configuration)).thenReturn(contextWrapper)
        whenever(contextWrapper.baseContext).thenReturn(context)
        // execute
        val actual: Context = context.createContext(newLocale)
        // validate
        assertEquals(contextWrapper, actual)
        assertEquals(context, (actual as ContextWrapper).baseContext)
        verify(configuration).setLocale(newLocale)
        verify(context).createConfigurationContext(configuration)
        verify(context).resources
        verify(contextWrapper).baseContext
        verify(resources).configuration
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.M])
    @Suppress("DEPRECATION")
    fun testCreateContextLegacy() {
        // setup
        whenever(context.resources).thenReturn(resources)
        whenever(resources.configuration).thenReturn(configuration)
        whenever(resources.displayMetrics).thenReturn(displayMetrics)
        // execute
        val actual: Context = context.createContext(newLocale)
        // validate
        assertEquals(context, actual)
        assertEquals(newLocale, configuration.locale)
        verify(resources).displayMetrics
        verify(resources).updateConfiguration(configuration, displayMetrics)
        verify(context).resources
        verify(resources).configuration
    }

    @Test
    fun testContextCompatColor() {
        // setup
        val expected = 200
        whenever(context.getColor(R.color.regular)).thenReturn(expected)
        // execute
        val actual = context.compatColor(R.color.regular)
        // validate
        assertEquals(expected, actual)
        verify(context).getColor(R.color.regular)
    }

    @Test
    fun testDrawableCompatTint() {
        // execute
        drawable.compatTint(R.color.regular)
        // validate
        verify(drawable).setTint(R.color.regular)
    }

}