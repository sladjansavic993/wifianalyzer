
package com.sladjan.wifianalyzer.wifi.accesspoint

import android.os.Build
import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.RobolectricUtil
import com.sladjan.wifianalyzer.wifi.band.WiFiWidth
import com.sladjan.wifianalyzer.wifi.model.WiFiAdditional
import com.sladjan.wifianalyzer.wifi.model.WiFiDetail
import com.sladjan.wifianalyzer.wifi.model.WiFiIdentifier
import com.sladjan.wifianalyzer.wifi.model.WiFiSignal
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class AccessPointPopupTest {
    private val mainActivity = RobolectricUtil.INSTANCE.activity
    private val fixture = AccessPointPopup()

    @Test
    fun testShowOpensPopup() {
        // setup
        val view = mainActivity.layoutInflater.inflate(R.layout.access_point_view_popup, null)
        // execute
        val actual = fixture.show(view)
        // validate
        assertNotNull(actual)
        assertTrue(actual.isShowing)
    }

    @Test
    fun testPopupIsClosedOnCloseButtonClick() {
        // setup
        val view = mainActivity.layoutInflater.inflate(R.layout.access_point_view_popup, null)
        val dialog = fixture.show(view)
        val closeButton = dialog.findViewById<View>(R.id.popupButtonClose)
        // execute
        closeButton.performClick()
        // validate
        assertFalse(dialog.isShowing)
    }

    @Test
    fun testAttach() {
        // setup
        val wiFiDetail = withWiFiDetail()
        val view = mainActivity.layoutInflater.inflate(R.layout.access_point_view_compact, null)
        // execute
        fixture.attach(view, wiFiDetail)
        // validate
        assertTrue(view.performClick())
    }

    private fun withWiFiDetail(): WiFiDetail =
            WiFiDetail(
                    WiFiIdentifier("SSID", "BSSID"),
                    "capabilities",
                    WiFiSignal(1, 1, WiFiWidth.MHZ_40, 2, true),
                    WiFiAdditional.EMPTY)
}