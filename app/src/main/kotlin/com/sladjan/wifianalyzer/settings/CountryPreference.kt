
package com.sladjan.wifianalyzer.settings

import android.content.Context
import android.util.AttributeSet
import com.sladjan.util.defaultCountryCode
import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.wifi.band.WiFiChannelCountry
import java.util.*

private fun data(): List<Data> {
    val currentLocale: Locale = INSTANCE.settings.languageLocale()
    return WiFiChannelCountry.findAll()
            .map { Data(it.countryCode(), it.countryName(currentLocale)) }
            .sorted()
}

class CountryPreference(context: Context, attrs: AttributeSet) :
        CustomPreference(context, attrs, data(), defaultCountryCode())
