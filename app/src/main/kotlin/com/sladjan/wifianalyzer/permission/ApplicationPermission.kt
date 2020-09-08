
package com.sladjan.wifianalyzer.permission

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import com.sladjan.annotation.OpenClass
import com.sladjan.util.buildMinVersionM

@OpenClass
class ApplicationPermission(private val activity: Activity, private val permissionDialog: PermissionDialog = PermissionDialog(activity)) {
    fun check() {
        if (granted()) {
            return
        }
        if (activity.isFinishing) {
            return
        }
        permissionDialog.show()
    }

    fun granted(requestCode: Int, grantResults: IntArray): Boolean =
            requestCode == REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED

    fun granted(): Boolean = !buildMinVersionM() || grantedAndroidM()

    @TargetApi(Build.VERSION_CODES.M)
    private fun grantedAndroidM(): Boolean =
            activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    companion object {
        internal val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        internal const val REQUEST_CODE = 0x123450
    }

}