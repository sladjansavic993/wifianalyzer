//SS
package com.sladjan.wifianalyzer.wifi.model

import com.sladjan.util.EMPTY
import com.sladjan.wifianalyzer.R
import java.util.*

private const val RSN = "RSN"

enum class Security(val imageResource: Int, val additional: String = String.EMPTY) {
    NONE(R.drawable.ic_lock_open),
    WPS(R.drawable.ic_lock_outline),
    WEP(R.drawable.ic_lock_outline),
    WPA(R.drawable.ic_lock),
    WPA2(R.drawable.ic_lock),
    WPA3(R.drawable.ic_lock, RSN);

    companion object {
        fun findAll(capabilities: String): Set<Security> =
                parse(capabilities).mapNotNull(transform()).toSortedSet().ifEmpty { setOf(NONE) }

        fun findOne(capabilities: String): Security = findAll(capabilities).first()

        private fun transform(): (String) -> Security? = {
            try {
                enumValueOf<Security>(it)
            } catch (e: IllegalArgumentException) {
                enumValues<Security>().find { security -> security.additional == it }
            }
        }

        private fun parse(capabilities: String): List<String> =
                capabilities
                        .toUpperCase(Locale.getDefault())
                        .replace("][", "-")
                        .replace("]", "")
                        .replace("[", "")
                        .split("-")
    }

}
