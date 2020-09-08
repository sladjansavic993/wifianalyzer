
package com.sladjan.wifianalyzer.wifi.filter

import android.app.AlertDialog
import android.content.DialogInterface
import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.navigation.NavigationMenu

class Filter(val alertDialog: AlertDialog?) {
    private var ssidFilter: SSIDFilter? = null
    internal var wiFiBandFilter: WiFiBandFilter? = null
        private set
    internal var strengthFilter: StrengthFilter? = null
        private set
    internal var securityFilter: SecurityFilter? = null
        private set

    fun show() {
        if (alertDialog != null && !alertDialog.isShowing) {
            alertDialog.show()
            wiFiBandFilter = addWiFiBandFilter(alertDialog)
            ssidFilter = addSSIDFilter(alertDialog)
            strengthFilter = addStrengthFilter(alertDialog)
            securityFilter = addSecurityFilter(alertDialog)
        }
    }

    private fun addSSIDFilter(alertDialog: AlertDialog): SSIDFilter =
            SSIDFilter(INSTANCE.filtersAdapter.ssidAdapter(), alertDialog)

    private fun addWiFiBandFilter(alertDialog: AlertDialog): WiFiBandFilter? =
            if (NavigationMenu.ACCESS_POINTS == INSTANCE.mainActivity.currentNavigationMenu()) {
                WiFiBandFilter(INSTANCE.filtersAdapter.wiFiBandAdapter(), alertDialog)
            } else null

    private fun addStrengthFilter(alertDialog: AlertDialog): StrengthFilter =
            StrengthFilter(INSTANCE.filtersAdapter.strengthAdapter(), alertDialog)

    private fun addSecurityFilter(alertDialog: AlertDialog): SecurityFilter =
            SecurityFilter(INSTANCE.filtersAdapter.securityAdapter(), alertDialog)

    private class Close : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface, which: Int) {
            dialog.dismiss()
            INSTANCE.filtersAdapter.reload()
        }
    }

    private class Apply : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface, which: Int) {
            dialog.dismiss()
            INSTANCE.filtersAdapter.save()
            INSTANCE.mainActivity.update()
        }
    }

    private class Reset : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface, which: Int) {
            dialog.dismiss()
            INSTANCE.filtersAdapter.reset()
            INSTANCE.mainActivity.update()
        }
    }

    companion object {
        fun build(): Filter {
            return Filter(buildAlertDialog())
        }

        private fun buildAlertDialog(): AlertDialog? {
            if (INSTANCE.mainActivity.isFinishing) {
                return null
            }
            val view = INSTANCE.layoutInflater.inflate(R.layout.filter_popup, null)
            return AlertDialog.Builder(view.context)
                    .setView(view)
                    .setTitle(R.string.filter_title)
                    .setIcon(R.drawable.ic_filter_list)
                    .setNegativeButton(R.string.filter_reset, Reset())
                    .setNeutralButton(R.string.filter_close, Close())
                    .setPositiveButton(R.string.filter_apply, Apply())
                    .create()
        }
    }

}