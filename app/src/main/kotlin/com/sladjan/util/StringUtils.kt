package com.sladjan.util

import android.annotation.TargetApi
import android.os.Build
import android.text.Html
import android.text.Spanned

val String.Companion.EMPTY: String get() = ""
val String.Companion.SPACE_SEPARATOR: String get() = " "

fun String.specialTrim(): String = this.trim { it <= ' ' }.replace(" +".toRegex(), String.SPACE_SEPARATOR)

fun String.toHtml(color: Int, small: Boolean): String =
        "<font color='" + color + "'><" + (if (small) "small" else "strong") +
                ">" + this + "</" + (if (small) "small" else "strong") + "></font>"

fun String.fromHtml(): Spanned =
        if (buildMinVersionN()) fromHtmlAndroidN() else fromHtmlLegacy()

@TargetApi(Build.VERSION_CODES.N)
private fun String.fromHtmlAndroidN(): Spanned = Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)

@Suppress("DEPRECATION")
private fun String.fromHtmlLegacy(): Spanned = Html.fromHtml(this)

