
package com.sladjan.wifianalyzer.settings

import androidx.annotation.StyleRes
import com.sladjan.wifianalyzer.R

enum class ThemeStyle(@param:StyleRes val theme: Int, @param:StyleRes val themeNoActionBar: Int) {
    DARK(R.style.ThemeDark, R.style.ThemeDarkNoActionBar),
    LIGHT(R.style.ThemeLight, R.style.ThemeLightNoActionBar),
    SYSTEM(R.style.ThemeSystem, R.style.ThemeSystemNoActionBar);
}