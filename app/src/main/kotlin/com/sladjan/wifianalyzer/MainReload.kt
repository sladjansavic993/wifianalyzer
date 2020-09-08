
package com.sladjan.wifianalyzer

import com.sladjan.wifianalyzer.settings.Settings
import com.sladjan.wifianalyzer.settings.ThemeStyle
import com.sladjan.wifianalyzer.wifi.accesspoint.ConnectionViewType
import java.util.*

class MainReload(settings: Settings) {
    var themeStyle: ThemeStyle
        private set
    var connectionViewType: ConnectionViewType
        private set
    var languageLocale: Locale
        private set

    fun shouldReload(settings: Settings): Boolean =
            themeChanged(settings) || connectionViewTypeChanged(settings) || languageChanged(settings)

    private fun connectionViewTypeChanged(settings: Settings): Boolean {
        val currentConnectionViewType = settings.connectionViewType()
        val connectionViewTypeChanged = connectionViewType != currentConnectionViewType
        if (connectionViewTypeChanged) {
            connectionViewType = currentConnectionViewType
        }
        return connectionViewTypeChanged
    }

    private fun themeChanged(settings: Settings): Boolean {
        val settingThemeStyle = settings.themeStyle()
        val themeChanged = themeStyle != settingThemeStyle
        if (themeChanged) {
            themeStyle = settingThemeStyle
        }
        return themeChanged
    }

    private fun languageChanged(settings: Settings): Boolean {
        val settingLanguageLocale = settings.languageLocale()
        val languageLocaleChanged = languageLocale != settingLanguageLocale
        if (languageLocaleChanged) {
            languageLocale = settingLanguageLocale
        }
        return languageLocaleChanged
    }

    init {
        themeStyle = settings.themeStyle()
        connectionViewType = settings.connectionViewType()
        languageLocale = settings.languageLocale()
    }
}