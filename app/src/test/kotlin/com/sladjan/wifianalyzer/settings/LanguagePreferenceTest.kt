//SS
package com.sladjan.wifianalyzer.settings

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sladjan.util.supportedLanguages
import com.sladjan.util.toLanguageTag
import com.sladjan.wifianalyzer.RobolectricUtil
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class LanguagePreferenceTest {
    private val mainActivity = RobolectricUtil.INSTANCE.activity
    private val languages = supportedLanguages()
    private val fixture = LanguagePreference(mainActivity, Robolectric.buildAttributeSet().build())

    @Test
    fun testEntries() {
        // execute
        val actual: Array<CharSequence> = fixture.entries
        // validate
        Assert.assertEquals(languages.size, actual.size)
        languages.forEach {
            val displayName: String = it.getDisplayName(it).capitalize()
            Assert.assertTrue(displayName, actual.contains(displayName))
        }
    }

    @Test
    fun testEntryValues() {
        // execute
        val actual: Array<CharSequence> = fixture.entryValues
        // validate
        Assert.assertEquals(languages.size, actual.size)
        languages.forEach {
            val languageTag: String = toLanguageTag(it)
            Assert.assertTrue(languageTag, actual.contains(languageTag))
        }
    }
}