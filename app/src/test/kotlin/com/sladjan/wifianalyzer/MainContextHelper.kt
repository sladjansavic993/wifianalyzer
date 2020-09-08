
package com.sladjan.wifianalyzer

import com.nhaarman.mockitokotlin2.mock
import com.sladjan.wifianalyzer.settings.Settings
import com.sladjan.wifianalyzer.vendor.model.VendorService
import com.sladjan.wifianalyzer.wifi.filter.adapter.FiltersAdapter
import com.sladjan.wifianalyzer.wifi.scanner.ScannerService

enum class MainContextHelper {
    INSTANCE;

    private val saved: MutableMap<Class<*>, Any> = mutableMapOf()
    private val mainContext: MainContext = MainContext.INSTANCE

    val settings: Settings
        get() {
            try {
                saved[Settings::class.java] = mainContext.settings
            } catch (e: UninitializedPropertyAccessException) {
                /* do nothing */
            }
            mainContext.settings = mock()
            return mainContext.settings
        }

    val vendorService: VendorService
        get() {
            try {
                saved[VendorService::class.java] = mainContext.vendorService
            } catch (e: UninitializedPropertyAccessException) {
                /* do nothing */
            }
            mainContext.vendorService = mock()
            return mainContext.vendorService
        }

    val scannerService: ScannerService
        get() {
            try {
                saved[ScannerService::class.java] = mainContext.scannerService
            } catch (e: UninitializedPropertyAccessException) {
                /* do nothing */
            }
            mainContext.scannerService = mock()
            return mainContext.scannerService
        }

    val mainActivity: MainActivity
        get() {
            try {
                saved[MainActivity::class.java] = mainContext.mainActivity
            } catch (e: UninitializedPropertyAccessException) {
                /* do nothing */
            }
            mainContext.mainActivity = mock()
            return mainContext.mainActivity
        }

    val configuration: Configuration
        get() {
            try {
                saved[Configuration::class.java] = mainContext.configuration
            } catch (e: UninitializedPropertyAccessException) {
                /* do nothing */
            }
            mainContext.configuration = mock()
            return mainContext.configuration
        }

    val filterAdapter: FiltersAdapter
        get() {
            try {
                saved[FiltersAdapter::class.java] = mainContext.filtersAdapter
            } catch (e: UninitializedPropertyAccessException) {
                /* do nothing */
            }
            mainContext.filtersAdapter = mock()
            return mainContext.filtersAdapter
        }

    fun restore() {
        saved.entries.forEach {
            when (it.key) {
                Settings::class.java -> mainContext.settings = it.value as Settings
                VendorService::class.java -> mainContext.vendorService = it.value as VendorService
                ScannerService::class.java -> mainContext.scannerService = it.value as ScannerService
                MainActivity::class.java -> mainContext.mainActivity = it.value as MainActivity
                Configuration::class.java -> mainContext.configuration = it.value as Configuration
                FiltersAdapter::class.java -> mainContext.filtersAdapter = it.value as FiltersAdapter
            }
        }
        saved.clear()
    }

}
