//SS
package com.sladjan.wifianalyzer.vendor

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sladjan.util.EMPTY
import com.sladjan.wifianalyzer.MainContextHelper.INSTANCE
import com.sladjan.wifianalyzer.RobolectricUtil
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class VendorFragmentTest {
    private val mainActivity = RobolectricUtil.INSTANCE.activity
    private val vendorService = INSTANCE.vendorService
    private val fixture = VendorFragment()

    @Before
    fun setUp() {
        whenever(vendorService.findVendors()).thenReturn(listOf())
        RobolectricUtil.INSTANCE.startFragment(fixture)
    }

    @After
    fun tearDown() {
        verify(vendorService).findVendors()
        INSTANCE.restore()
    }

    @Test
    fun testListenerOnQueryTextChange() {
        // setup
        val values = "     ABS       ADF      "
        val expected = "ABS ADF"
        val vendorAdapter: VendorAdapter = mock()
        val fixture = VendorFragment.Listener(vendorAdapter)
        // execute
        val actual = fixture.onQueryTextChange(values)
        // verify
        assertTrue(actual)
        verify(vendorAdapter).update(expected)
    }

    @Test
    fun testListenerOnQueryTextChangeWithNull() {
        // setup
        val vendorAdapter: VendorAdapter = mock()
        val fixture = VendorFragment.Listener(vendorAdapter)
        // execute
        val actual = fixture.onQueryTextChange(String.EMPTY)
        // verify
        assertTrue(actual)
        verify(vendorAdapter).update(String.EMPTY)
    }

    @Test
    fun testListenerOnQueryTextSubmit() {
        // setup
        val vendorAdapter: VendorAdapter = mock()
        val fixture = VendorFragment.Listener(vendorAdapter)
        // execute
        val actual = fixture.onQueryTextSubmit(String.EMPTY)
        // verify
        assertFalse(actual)
    }
}