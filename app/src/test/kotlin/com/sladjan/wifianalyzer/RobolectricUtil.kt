
package com.sladjan.wifianalyzer

import android.os.Looper
import androidx.fragment.app.Fragment
import org.robolectric.Robolectric
import org.robolectric.Shadows

enum class RobolectricUtil {
    INSTANCE;

    val activity: MainActivity = Robolectric.buildActivity(MainActivity::class.java).create().resume().get()

    fun startFragment(fragment: Fragment) {
        val fragmentManager = activity.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(fragment, null)
        fragmentTransaction.commit()
        clearLooper()
    }

    fun clearLooper() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
    }

}