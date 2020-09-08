//SS
package com.sladjan.wifianalyzer.settings

import com.sladjan.wifianalyzer.R
import org.junit.Assert.assertEquals
import org.junit.Test

class ThemeStyleTest {
    @Test
    fun testThemeStyle() {
        assertEquals(3, ThemeStyle.values().size)
    }

    @Test
    fun testTheme() {
        assertEquals(R.style.ThemeLight, ThemeStyle.LIGHT.theme)
        assertEquals(R.style.ThemeDark, ThemeStyle.DARK.theme)
        assertEquals(R.style.ThemeSystem, ThemeStyle.SYSTEM.theme)
    }

    @Test
    fun testThemeNoActionBar() {
        assertEquals(R.style.ThemeDarkNoActionBar, ThemeStyle.DARK.themeNoActionBar)
        assertEquals(R.style.ThemeLightNoActionBar, ThemeStyle.LIGHT.themeNoActionBar)
        assertEquals(R.style.ThemeSystemNoActionBar, ThemeStyle.SYSTEM.themeNoActionBar)
    }

}