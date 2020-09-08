
package com.sladjan.wifianalyzer.wifi.accesspoint

import android.app.Dialog
import android.view.View
import com.sladjan.annotation.OpenClass
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.wifi.model.WiFiDetail

@OpenClass
class AccessPointPopup {
    fun show(view: View): Dialog {
        val dialog = Dialog(view.context)
        dialog.setContentView(view)
        dialog.findViewById<View>(R.id.popupButtonClose)
                .setOnClickListener { dialog.dismiss() }
        dialog.show()
        return dialog
    }

    fun attach(view: View, wiFiDetail: WiFiDetail) {
        view.setOnClickListener {
            try {
                show(AccessPointDetail().makeViewDetailed(wiFiDetail))
            } catch (e: Exception) {
                // do nothing
            }
        }
    }

}