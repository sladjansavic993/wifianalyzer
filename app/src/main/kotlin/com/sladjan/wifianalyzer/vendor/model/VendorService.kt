
package com.sladjan.wifianalyzer.vendor.model

import android.content.res.Resources
import com.sladjan.annotation.OpenClass
import com.sladjan.util.EMPTY
import com.sladjan.util.readFile
import com.sladjan.wifianalyzer.R
import java.util.*

@OpenClass
class VendorService(private val resources: Resources) {
    private val vendorData: VendorData by lazy { load(resources) }

    fun findVendorName(address: String = String.EMPTY): String =
            vendorData.macs[address.clean()].orEmpty()

    fun findMacAddresses(vendorName: String = String.EMPTY): List<String> =
            vendorData.vendors[vendorName.toUpperCase(Locale.getDefault())].orEmpty()

    fun findVendors(vendorName: String = String.EMPTY): List<String> {
        val name = vendorName.toUpperCase(Locale.getDefault())
        return vendorData.vendors.filterKeys { filter(it, name) }.keys.toList()
    }

    internal fun findMacs(): List<String> = vendorData.macs.keys.toList()

    private fun filter(source: String, filter: String): Boolean =
            source.contains(filter) || macContains(source, filter)

    private fun macContains(source: String, filter: String): Boolean =
            findMacAddresses(source).any { it.contains(filter) }

    private fun load(resources: Resources): VendorData {
        val macs: MutableMap<String, String> = TreeMap()
        val vendors: MutableMap<String, List<String>> = TreeMap()
        readFile(resources, R.raw.data)
                .split("\n")
                .map { it.split("|").toTypedArray() }
                .filter { it.size == 2 }
                .forEach { it ->
                    val name = it[0]
                    val results: List<String> = it[1].chunked(MAX_SIZE)
                    results.forEach { macs[it] = name }
                    vendors[name] = results.map { it.toMacAddress() }
                }
        return VendorData(vendors, macs)
    }

    private class VendorData(val vendors: Map<String, List<String>>, val macs: Map<String, String>)

}