
package com.sladjan.wifianalyzer.navigation.items

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.MenuItem
import com.sladjan.annotation.OpenClass
import com.sladjan.wifianalyzer.MainActivity
import com.sladjan.wifianalyzer.navigation.NavigationMenu

@OpenClass
internal class PortAuthorityItem : NavigationItem {
    override fun activate(mainActivity: MainActivity, menuItem: MenuItem, navigationMenu: NavigationMenu) {
        try {
            val context: Context = mainActivity.applicationContext
            val packageManager: PackageManager = context.packageManager
            val intent: Intent = packageManager.getLaunchIntentForPackage(PORT_AUTHORITY_DONATE)
                    ?: let {
                        packageManager.getLaunchIntentForPackage(PORT_AUTHORITY_FREE)
                                ?: redirectToPlayStore()
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            // No Store or Port Authority Available
        }
    }

    fun redirectToPlayStore(): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("market://details?id=$PORT_AUTHORITY_FREE")
        return intent
    }

    companion object {
        private const val PORT_AUTHORITY = "com.aaronjwood.portauthority."
        private const val PORT_AUTHORITY_FREE = PORT_AUTHORITY + "free"
        private const val PORT_AUTHORITY_DONATE = PORT_AUTHORITY + "donate"
    }
}