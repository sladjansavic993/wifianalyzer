//SS
package com.sladjan.wifianalyzer.permission

import android.app.Activity
import android.content.DialogInterface
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.sladjan.wifianalyzer.RobolectricUtil
import com.sladjan.wifianalyzer.permission.PermissionDialog.CancelClick
import com.sladjan.wifianalyzer.permission.PermissionDialog.OkClick
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class PermissionDialogTest {
    private val activity = RobolectricUtil.INSTANCE.activity
    private val fixture = PermissionDialog(activity)

    @Test
    fun testShow() {
        // execute
        fixture.show()
    }

    @Test
    fun testOkClick() {
        // setup
        val activity: Activity = mock()
        val dialog: DialogInterface = mock()
        val fixture = OkClick(activity)
        // execute
        fixture.onClick(dialog, 0)
        // validate
        verify(activity).requestPermissions(ApplicationPermission.PERMISSIONS, ApplicationPermission.REQUEST_CODE)
        verify(dialog).dismiss()
        verifyNoMoreInteractions(activity)
        verifyNoMoreInteractions(dialog)
    }

    @Test
    fun testCancelClick() {
        // setup
        val activity: Activity = mock()
        val dialog: DialogInterface = mock()
        val fixture = CancelClick(activity)
        // execute
        fixture.onClick(dialog, 0)
        // validate
        verify(activity).finish()
        verify(dialog).dismiss()
        verifyNoMoreInteractions(activity)
        verifyNoMoreInteractions(dialog)
    }
}