//SS
package com.sladjan.wifianalyzer.permission

import android.app.Activity
import com.sladjan.annotation.OpenClass

@OpenClass
class PermissionService(
        private val activity: Activity,
        private val systemPermission: SystemPermission = SystemPermission(activity),
        private val applicationPermission: ApplicationPermission = ApplicationPermission(activity)) {

    fun enabled(): Boolean = systemEnabled() && permissionGranted()

    fun systemEnabled(): Boolean = systemPermission.enabled()

    fun check(): Unit = applicationPermission.check()

    fun granted(requestCode: Int, grantResults: IntArray): Boolean =
            applicationPermission.granted(requestCode, grantResults)

    fun permissionGranted(): Boolean = applicationPermission.granted()
}