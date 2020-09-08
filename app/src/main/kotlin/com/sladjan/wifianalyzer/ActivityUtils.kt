package com.sladjan.wifianalyzer

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar

internal fun MainActivity.keepScreenOn() =
        if (MainContext.INSTANCE.settings.keepScreenOn()) {
            this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            this.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

internal fun MainActivity.setupToolbar(): Toolbar {
    val toolbar: Toolbar = this.findViewById(R.id.toolbar)
    toolbar.setOnClickListener { toggleWiFiBand() }
    this.setSupportActionBar(toolbar)
    this.supportActionBar?.let {
        it.setHomeButtonEnabled(true)
        it.setDisplayHomeAsUpEnabled(true)
    }
    return toolbar
}

internal fun MainActivity.toggleWiFiBand() {
    if (this.currentNavigationMenu().wiFiBandSwitchable()) {
        MainContext.INSTANCE.settings.toggleWiFiBand()
    }
}

internal fun makeIntent(action: String): Intent = Intent(action)

@TargetApi(Build.VERSION_CODES.Q)
internal fun MainActivity.startWiFiSettings() =
        this.startActivityForResult(makeIntent(Settings.Panel.ACTION_WIFI), 0)

internal fun MainActivity.startLocationSettings() =
        this.startActivity(makeIntent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))

