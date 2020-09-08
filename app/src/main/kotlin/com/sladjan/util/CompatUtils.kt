package com.sladjan.util

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import java.util.*

fun Context.createContext(newLocale: Locale): Context =
        if (buildMinVersionN()) {
            createContextAndroidN(newLocale)
        } else {
            createContextLegacy(newLocale)
        }

@TargetApi(Build.VERSION_CODES.N)
private fun Context.createContextAndroidN(newLocale: Locale): Context {
    val resources: Resources = resources
    val configuration: Configuration = resources.configuration
    configuration.setLocale(newLocale)
    return createConfigurationContext(configuration)
}

@Suppress("DEPRECATION")
private fun Context.createContextLegacy(newLocale: Locale): Context {
    val resources: Resources = resources
    val configuration: Configuration = resources.configuration
    configuration.locale = newLocale
    resources.updateConfiguration(configuration, resources.displayMetrics)
    return this
}

@ColorInt
fun Context.compatColor(@ColorRes id: Int): Int =
        if (buildMinVersionM()) {
            getColor(id)
        } else {
            ContextCompat.getColor(this, id)
        }

fun Drawable.compatTint(@ColorInt tint: Int): Unit =
        if (buildMinVersionL()) {
            setTint(tint)
        } else {
            DrawableCompat.setTint(this, tint)
        }
