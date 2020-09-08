
package com.sladjan.wifianalyzer.wifi.model

import com.sladjan.annotation.OpenClass
import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.wifi.predicate.Predicate

@OpenClass
class WiFiData(val wiFiDetails: List<WiFiDetail>, val wiFiConnection: WiFiConnection) {

    fun connection(): WiFiDetail =
            wiFiDetails
                    .find { connected(it) }
                    ?.let { copy(it) }
                    ?: WiFiDetail.EMPTY

    fun wiFiDetails(predicate: Predicate, sortBy: SortBy): List<WiFiDetail> =
            wiFiDetails(predicate, sortBy, GroupBy.NONE)

    fun wiFiDetails(predicate: Predicate, sortBy: SortBy, groupBy: GroupBy): List<WiFiDetail> {
        val connection: WiFiDetail = connection()
        return wiFiDetails
                .filter { predicate.test(it) }
                .map { transform(it, connection) }
                .sortAndGroup(sortBy, groupBy)
                .sortedWith(sortBy.sort)
    }

    private fun List<WiFiDetail>.sortAndGroup(sortBy: SortBy, groupBy: GroupBy): List<WiFiDetail> =
            if (groupBy.none) {
                this
            } else {
                this.groupBy { groupBy.group(it) }
                        .values
                        .map(map(sortBy, groupBy))
                        .sortedWith(sortBy.sort)
            }

    private fun map(sortBy: SortBy, groupBy: GroupBy): (List<WiFiDetail>) -> WiFiDetail = {
        val sortedWith: List<WiFiDetail> = it.sortedWith(groupBy.sort)
        when (sortedWith.size) {
            1 -> sortedWith.first()
            else ->
                WiFiDetail(
                        sortedWith.first(),
                        sortedWith.subList(1, sortedWith.size).sortedWith(sortBy.sort))
        }
    }

    private fun transform(wiFiDetail: WiFiDetail, connection: WiFiDetail): WiFiDetail =
            when (wiFiDetail) {
                connection -> connection
                else -> {
                    val vendorName: String = INSTANCE.vendorService.findVendorName(wiFiDetail.wiFiIdentifier.bssid)
                    val wiFiAdditional = WiFiAdditional(vendorName, WiFiConnection.EMPTY)
                    WiFiDetail(wiFiDetail, wiFiAdditional)
                }
            }

    private fun connected(it: WiFiDetail): Boolean =
            wiFiConnection.wiFiIdentifier.equals(it.wiFiIdentifier, true)

    private fun copy(wiFiDetail: WiFiDetail): WiFiDetail {
        val vendorName: String = INSTANCE.vendorService.findVendorName(wiFiDetail.wiFiIdentifier.bssid)
        val wiFiAdditional = WiFiAdditional(vendorName, wiFiConnection)
        return WiFiDetail(wiFiDetail, wiFiAdditional)
    }

    companion object {
        val EMPTY = WiFiData(listOf(), WiFiConnection.EMPTY)
    }

}