
package com.sladjan.wifianalyzer.wifi.accesspoint

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.sladjan.annotation.OpenClass
import com.sladjan.util.compatColor
import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.wifi.model.WiFiAdditional
import com.sladjan.wifianalyzer.wifi.model.WiFiDetail
import com.sladjan.wifianalyzer.wifi.model.WiFiSignal

@OpenClass
class AccessPointDetail {
    private val vendorShortMax = 12
    private val vendorLongMax = 30

    fun makeView(convertView: View?,
                 parent: ViewGroup?,
                 wiFiDetail: WiFiDetail,
                 child: Boolean = false,
                 @LayoutRes layout: Int = INSTANCE.settings.accessPointView().layout)
            : View {
        val view = convertView ?: INSTANCE.layoutInflater.inflate(layout, parent, false)
        setViewCompact(view, wiFiDetail, child)
        setViewExtra(view, wiFiDetail)
        setViewVendorShort(view, wiFiDetail.wiFiAdditional)
        return view
    }

    fun makeViewDetailed(wiFiDetail: WiFiDetail): View {
        val view = INSTANCE.layoutInflater.inflate(R.layout.access_point_view_popup, null)
        setViewCompact(view, wiFiDetail, false)
        setViewExtra(view, wiFiDetail)
        setViewVendorLong(view, wiFiDetail.wiFiAdditional)
        setView80211mc(view, wiFiDetail.wiFiSignal)
        enableTextSelection(view)
        return view
    }

    private fun setView80211mc(view: View, wiFiSignal: WiFiSignal) =
            view.findViewById<TextView>(R.id.flag80211mc)?.let {
                it.visibility = if (wiFiSignal.is80211mc) View.VISIBLE else View.GONE
            }

    private fun enableTextSelection(view: View) =
            view.findViewById<TextView>(R.id.ssid)?.let {
                it.setTextIsSelectable(true)
                view.findViewById<TextView>(R.id.vendorLong).setTextIsSelectable(true)
            }

    private fun setViewCompact(view: View, wiFiDetail: WiFiDetail, child: Boolean) =
            view.findViewById<TextView>(R.id.ssid)?.let {
                it.text = wiFiDetail.wiFiIdentifier.title()
                val wiFiSignal = wiFiDetail.wiFiSignal
                val securityImage = view.findViewById<ImageView>(R.id.securityImage)
                securityImage.setImageResource(wiFiDetail.security().imageResource)
                val textLevel = view.findViewById<TextView>(R.id.level)
                textLevel.text = "${wiFiSignal.level}dBm"
                textLevel.setTextColor(view.context.compatColor(wiFiSignal.strength().colorResource()))
                view.findViewById<TextView>(R.id.channel).text = wiFiSignal.channelDisplay()
                view.findViewById<TextView>(R.id.primaryFrequency).text = "${wiFiSignal.primaryFrequency}${WiFiSignal.FREQUENCY_UNITS}"
                view.findViewById<TextView>(R.id.distance).text = wiFiSignal.distance()
                view.findViewById<View>(R.id.tab).visibility = if (child) View.VISIBLE else View.GONE
            }

    private fun setViewExtra(view: View, wiFiDetail: WiFiDetail) =
            view.findViewById<TextView>(R.id.channel_frequency_range)?.let {
                val wiFiSignal = wiFiDetail.wiFiSignal
                with(view.findViewById<ImageView>(R.id.levelImage)) {
                    val strength = wiFiSignal.strength()
                    this.setImageResource(strength.imageResource())
                    this.setColorFilter(view.context.compatColor(strength.colorResource()))
                }
                it.text = "${wiFiSignal.frequencyStart()} - ${wiFiSignal.frequencyEnd()}"
                view.findViewById<TextView>(R.id.width).text = "(${wiFiSignal.wiFiWidth.frequencyWidth}${WiFiSignal.FREQUENCY_UNITS})"
                view.findViewById<TextView>(R.id.capabilities).text = wiFiDetail.capabilities
            }

    private fun setViewVendorShort(view: View, wiFiAdditional: WiFiAdditional) =
            view.findViewById<TextView>(R.id.vendorShort)?.let {
                if (wiFiAdditional.vendorName.isBlank()) {
                    it.visibility = View.GONE
                } else {
                    it.visibility = View.VISIBLE
                    it.text = wiFiAdditional.vendorName.take(vendorShortMax)
                }
            }

    private fun setViewVendorLong(view: View, wiFiAdditional: WiFiAdditional) =
            view.findViewById<TextView>(R.id.vendorLong)?.let {
                if (wiFiAdditional.vendorName.isBlank()) {
                    it.visibility = View.GONE
                } else {
                    it.visibility = View.VISIBLE
                    it.text = wiFiAdditional.vendorName.take(vendorLongMax)
                }
            }

}