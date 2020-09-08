

package com.sladjan.wifianalyzer.wifi.model

import com.sladjan.util.EMPTY

data class WiFiConnection(val wiFiIdentifier: WiFiIdentifier = WiFiIdentifier.EMPTY,
                          val ipAddress: String = String.EMPTY,
                          val linkSpeed: Int = LINK_SPEED_INVALID) :
        Comparable<WiFiConnection> {

    fun connected(): Boolean = EMPTY != this

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WiFiConnection

        return wiFiIdentifier == other.wiFiIdentifier
    }

    override fun hashCode(): Int = wiFiIdentifier.hashCode()

    override fun compareTo(other: WiFiConnection): Int = wiFiIdentifier.compareTo(other.wiFiIdentifier)

    companion object {
        const val LINK_SPEED_INVALID = -1

        val EMPTY = WiFiConnection()
    }
}

