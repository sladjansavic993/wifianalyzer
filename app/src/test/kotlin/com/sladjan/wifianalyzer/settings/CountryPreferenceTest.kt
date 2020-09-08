//SS
package com.sladjan.wifianalyzer.settings

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sladjan.wifianalyzer.RobolectricUtil
import com.sladjan.wifianalyzer.wifi.band.WiFiChannelCountry.Companion.findAll
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.util.*

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class CountryPreferenceTest {
    private val mainActivity = RobolectricUtil.INSTANCE.activity
    private val countries = findAll()
    private val fixture = CountryPreference(mainActivity, Robolectric.buildAttributeSet().build())
    private val currentLocale = Locale.getDefault()

    @Test
    fun testEntries() {
        // execute
        val actual: Array<CharSequence> = fixture.entries
        // validate
        Assert.assertEquals(countries.size, actual.size)
        countries.forEach {
            val countryName: String = it.countryName(currentLocale)
            Assert.assertTrue(countryName, actual.contains(countryName))
        }
    }

    @Test
    fun testEntryValues() {
        // execute
        val actual: Array<CharSequence> = fixture.entryValues
        // validate
        Assert.assertEquals(countries.size, actual.size)
        countries.forEach {
            val countryCode: String = it.countryCode()
            Assert.assertTrue(countryCode, actual.contains(countryCode))
        }
    }
}