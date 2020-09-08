//SS
package com.sladjan.wifianalyzer.permission

import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.view.View
import com.sladjan.annotation.OpenClass
import com.sladjan.util.buildMinVersionM
import com.sladjan.util.buildMinVersionP
import com.sladjan.wifianalyzer.R

@OpenClass
class PermissionDialog(private val activity: Activity) {
    fun show() {
        val view = activity.layoutInflater.inflate(R.layout.info_permission, null)
        val visibility = if (buildMinVersionP()) View.VISIBLE else View.GONE
        view.findViewById<View>(R.id.throttling)?.visibility = visibility
        AlertDialog.Builder(activity)
                .setView(view)
                .setTitle(R.string.app_full_name)
                .setIcon(R.drawable.ic_app)
                .setPositiveButton(android.R.string.ok, OkClick(activity))
                .setNegativeButton(android.R.string.cancel, CancelClick(activity))
                .create()
                .show()
    }

    internal class OkClick(private val activity: Activity) : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface, which: Int) {
            dialog.dismiss()
            requestPermissionsAndroidM()
        }

        @TargetApi(Build.VERSION_CODES.M)
        private fun requestPermissionsAndroidM() {
            if (buildMinVersionM()) {
                activity.requestPermissions(ApplicationPermission.PERMISSIONS, ApplicationPermission.REQUEST_CODE)
            }
        }

    }

    internal class CancelClick(private val activity: Activity) : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface, which: Int) {
            dialog.dismiss()
            activity.finish()
        }

    }

}