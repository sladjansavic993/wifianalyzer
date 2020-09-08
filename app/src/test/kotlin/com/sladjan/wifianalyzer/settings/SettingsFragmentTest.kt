//SS
package com.sladjan.wifianalyzer.settings

import android.os.Build
import androidx.preference.Preference
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.RobolectricUtil
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class SettingsFragmentTest {

    @Test
    fun testOnCreate() {
        // setup
        val fixture = SettingsFragment()
        RobolectricUtil.INSTANCE.startFragment(fixture)
        // validate
        Assert.assertNotNull(fixture.view)
    }

    @Test
    fun testExperimentalIsVisible() {
        // setup
        val fixture = SettingsFragment()
        RobolectricUtil.INSTANCE.startFragment(fixture)
        val key = fixture.getString(R.string.experimental_key)
        // execute
        val actual = fixture.findPreference<Preference>(key)
        // validate
        assertTrue(actual!!.isVisible)
    }

    @Test
    fun testWiFiOnExitIsVisible() {
        // setup
        val fixture = SettingsFragment()
        RobolectricUtil.INSTANCE.startFragment(fixture)
        val key = fixture.getString(R.string.wifi_off_on_exit_key)
        // execute
        val actual = fixture.findPreference<Preference>(key)
        // validate
        assertTrue(actual!!.isVisible)
    }

    @Test
    fun testExperimentalIsNotVisible() {
        // setup
        val fixture = SettingsFragmentQ()
        RobolectricUtil.INSTANCE.startFragment(fixture)
        val key = fixture.getString(R.string.experimental_key)
        // execute
        val actual = fixture.findPreference<Preference>(key)
        // validate
        assertFalse(actual!!.isVisible)
    }

    @Test
    fun testWiFiOnExitIsNotVisible() {
        // setup
        val fixture = SettingsFragmentQ()
        RobolectricUtil.INSTANCE.startFragment(fixture)
        val key = fixture.getString(R.string.wifi_off_on_exit_key)
        // execute
        val actual = fixture.findPreference<Preference>(key)
        // validate
        assertFalse(actual!!.isVisible)
    }

    class SettingsFragmentQ : SettingsFragment() {
        override fun minVersionQ(): Boolean = true

        override fun versionP(): Boolean = false
    }
}