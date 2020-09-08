//SS
package com.sladjan.wifianalyzer.vendor

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.*
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.MainContextHelper.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.RobolectricUtil
import com.sladjan.wifianalyzer.vendor.model.VendorService
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class VendorAdapterTest {
    private val vendorName1 = "N1"
    private val vendorName2 = "N2"
    private val vendorName3 = "N3"

    private lateinit var fixture: VendorAdapter

    private val mainActivity: MainActivity = RobolectricUtil.INSTANCE.activity
    private val vendorService: VendorService = INSTANCE.vendorService
    private val vendors: List<String> = listOf(vendorName1, vendorName2, vendorName3)
    private val macs: List<String> = listOf("MAC1", "MAC2", "MAC3")

    @Before
    fun setUp() {
        whenever(vendorService.findVendors()).thenReturn(vendors)
        fixture = VendorAdapter(mainActivity, vendorService)
    }

    @After
    fun tearDown() {
        verify(vendorService, atLeastOnce()).findVendors()
        verifyNoMoreInteractions(vendorService)
        INSTANCE.restore()
    }

    @Test
    fun testConstructor() {
        // validate
        assertEquals(vendors.size, fixture.count)
        assertEquals(vendors[0], fixture.getItem(0))
        assertEquals(vendors[1], fixture.getItem(1))
        assertEquals(vendors[2], fixture.getItem(2))
        verify(vendorService).findVendors()
    }

    @Test
    fun testGetView() {
        // setup
        val expected = macs.joinToString(separator = ", ")
        val viewGroup = mainActivity.findViewById<ViewGroup>(android.R.id.content)
        whenever(vendorService.findMacAddresses(vendorName2)).thenReturn(macs)
        // execute
        val actual = fixture.getView(1, null, viewGroup)
        // validate
        assertNotNull(actual)
        assertEquals(vendorName2, actual.findViewById<TextView>(R.id.vendor_name).text.toString())
        assertEquals(expected, actual.findViewById<TextView>(R.id.vendor_macs).text.toString())
        verify(vendorService).findMacAddresses(vendorName2)
        verify(vendorService, never()).findVendorName(vendorName1)
        verify(vendorService, never()).findVendorName(vendorName3)
    }

    @Test
    fun testUpdate() {
        // setup
        fixture = spy(VendorAdapter(mainActivity, vendorService))
        whenever(vendorService.findVendors(vendorName2)).thenReturn(vendors)
        doNothing().whenever(fixture).clear()
        doNothing().whenever(fixture).addAll(vendors)
        // execute
        fixture.update(vendorName2)
        // validate
        verify(vendorService).findVendors(vendorName2)
        verify(fixture).clear()
        verify(fixture).addAll(vendors)
    }

    @Test
    fun testGetViewWhenRootViewNotNull() {
        // setup
        val rootView: View = mock()
        val vendorNameView: TextView = mock()
        val vendorMacsView: TextView = mock()
        val viewGroup = mainActivity.findViewById<ViewGroup>(android.R.id.content)
        val expected = macs.joinToString(separator = ", ")
        whenever(vendorService.findMacAddresses(vendorName2)).thenReturn(macs)
        whenever(rootView.findViewById<TextView>(R.id.vendor_name)).thenReturn(vendorNameView)
        whenever(rootView.findViewById<TextView>(R.id.vendor_macs)).thenReturn(vendorMacsView)
        // execute
        val actual = fixture.getView(1, rootView, viewGroup)
        // validate
        assertNotNull(actual)
        verify(vendorNameView).text = vendorName2
        verify(vendorMacsView).text = expected
        verify(rootView).findViewById<TextView>(R.id.vendor_name)
        verify(rootView).findViewById<TextView>(R.id.vendor_macs)
        verify(vendorService).findMacAddresses(vendorName2)
    }

}