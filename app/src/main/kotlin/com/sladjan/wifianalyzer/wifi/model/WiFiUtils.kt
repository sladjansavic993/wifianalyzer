
package com.sladjan.wifianalyzer.wifi.model

import com.sladjan.util.EMPTY
import java.net.InetAddress
import java.nio.ByteOrder
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.pow

private const val DISTANCE_MHZ_M = 27.55
private const val MIN_RSSI = -100
private const val MAX_RSSI = -55
private const val QUOTE = "\""

fun calculateDistance(frequency: Int, level: Int): Double =
        10.0.pow((DISTANCE_MHZ_M - 20 * log10(frequency.toDouble()) + abs(level)) / 20.0)

fun calculateSignalLevel(rssi: Int, numLevels: Int): Int = when {
    rssi <= MIN_RSSI -> 0
    rssi >= MAX_RSSI -> numLevels - 1
    else -> (rssi - MIN_RSSI) * (numLevels - 1) / (MAX_RSSI - MIN_RSSI)
}

fun convertSSID(ssid: String): String = ssid.removePrefix(QUOTE).removeSuffix(QUOTE)

fun convertIpAddress(ipAddress: Int): String {
    return try {
        val value: Long = when (ByteOrder.LITTLE_ENDIAN) {
            ByteOrder.nativeOrder() -> Integer.reverseBytes(ipAddress).toLong()
            else -> ipAddress.toLong()
        }
        InetAddress.getByAddress(value.toBigInteger().toByteArray()).hostAddress
    } catch (e: Exception) {
        String.EMPTY
    }
}
