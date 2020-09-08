//SS
package com.sladjan.wifianalyzer.settings

import android.content.Context
import android.util.AttributeSet
import com.sladjan.util.defaultLanguageTag
import com.sladjan.util.supportedLanguages
import com.sladjan.util.toLanguageTag
import java.util.*

private fun data(): List<Data> = supportedLanguages()
        .map { map(it) }
        .sorted()

private fun map(it: Locale): Data = Data(toLanguageTag(it), it.getDisplayName(it).capitalize())

class LanguagePreference(context: Context, attrs: AttributeSet) :
        CustomPreference(context, attrs, data(), defaultLanguageTag())