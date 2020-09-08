
package com.sladjan.wifianalyzer.settings

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.sladjan.util.buildMinVersionQ
import com.sladjan.util.buildVersionP
import com.sladjan.wifianalyzer.R

open class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(bundle: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)
        findPreference<Preference>(getString(R.string.experimental_key))!!.isVisible = versionP()
        findPreference<Preference>(getString(R.string.wifi_off_on_exit_key))!!.isVisible = !minVersionQ()
    }

    open fun minVersionQ(): Boolean = buildMinVersionQ()

    open fun versionP(): Boolean = buildVersionP()
}