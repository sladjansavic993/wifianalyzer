

package com.sladjan.wifianalyzer.export

import android.content.Intent
import android.content.res.Resources
import com.sladjan.annotation.OpenClass
import com.sladjan.util.EMPTY
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.wifi.model.WiFiDetail
import com.sladjan.wifianalyzer.wifi.model.WiFiSignal.Companion.FREQUENCY_UNITS
import java.text.SimpleDateFormat
import java.util.*

@OpenClass
class Export(private val exportIntent: ExportIntent = ExportIntent()) {

    private val header = "Time Stamp|" +
            "SSID|" +
            "BSSID|" +
            "Strength|" +
            "Primary Channel|" +
            "Primary Frequency|" +
            "Center Channel|" +
            "Center Frequency|" +
            "Width (Range)|" +
            "Distance|" +
            "802.11mc|" +
            "Security" +
            "\n"

    fun export(mainActivity: MainActivity, wiFiDetails: List<WiFiDetail>): Intent =
            export(mainActivity, wiFiDetails, Date())

    fun export(mainActivity: MainActivity, wiFiDetails: List<WiFiDetail>, date: Date): Intent {
        val timestamp: String = timestamp(date)
        val title: String = title(mainActivity, timestamp)
        val data: String = data(wiFiDetails, timestamp)
        return exportIntent.intent(title, data)
    }

    internal fun data(wiFiDetails: List<WiFiDetail>, timestamp: String): String =
            header + wiFiDetails.joinToString(separator = String.EMPTY, transform = toExportString(timestamp))

    internal fun title(mainActivity: MainActivity, timestamp: String): String {
        val resources: Resources = mainActivity.resources
        val title: String = resources.getString(R.string.action_access_points)
        return "$title-$timestamp"
    }

    internal fun timestamp(date: Date): String = SimpleDateFormat(TIME_STAMP_FORMAT, Locale.US).format(date)

    private fun toExportString(timestamp: String): (WiFiDetail) -> String = {
        with(it) {
            "$timestamp|" +
                    "${wiFiIdentifier.ssid}|" +
                    "${wiFiIdentifier.bssid}|" +
                    "${wiFiSignal.level}dBm|" +
                    "${wiFiSignal.primaryWiFiChannel().channel}|" +
                    "${wiFiSignal.primaryFrequency}$FREQUENCY_UNITS|" +
                    "${wiFiSignal.centerWiFiChannel().channel}|" +
                    "${wiFiSignal.centerFrequency}$FREQUENCY_UNITS|" +
                    "${wiFiSignal.wiFiWidth.frequencyWidth}$FREQUENCY_UNITS (${wiFiSignal.frequencyStart()} - ${wiFiSignal.frequencyEnd()})|" +
                    "${wiFiSignal.distance()}|" +
                    "${wiFiSignal.is80211mc}|" +
                    capabilities +
                    "\n"
        }
    }

    companion object {
        private const val TIME_STAMP_FORMAT = "yyyy/MM/dd-HH:mm:ss"
    }

}