
package com.sladjan.wifianalyzer.vendor.model

import com.sladjan.util.EMPTY
import java.util.*

internal const val MAX_SIZE = 6
private const val SEPARATOR = ":"

internal fun String.clean(): String =
        orEmpty().replace(SEPARATOR, String.EMPTY).take(MAX_SIZE).toUpperCase(Locale.getDefault())

internal fun String.toMacAddress(): String =
        when {
            isEmpty() -> String.EMPTY
            length < MAX_SIZE -> "*$this*"
            else -> substring(0, 2) + SEPARATOR + substring(2, 4) + SEPARATOR + substring(4, 6)
        }
