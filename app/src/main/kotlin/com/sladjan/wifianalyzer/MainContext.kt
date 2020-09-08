
package com.sladjan.wifianalyzer

import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.view.LayoutInflater
import com.sladjan.wifianalyzer.settings.Repository
import com.sladjan.wifianalyzer.settings.Settings
import com.sladjan.wifianalyzer.vendor.model.VendorService
import com.sladjan.wifianalyzer.wifi.filter.adapter.FiltersAdapter
import com.sladjan.wifianalyzer.wifi.scanner.ScannerService
import com.sladjan.wifianalyzer.wifi.scanner.makeScannerService

enum class MainContext {
    INSTANCE;

    lateinit var settings: Settings
    lateinit var mainActivity: MainActivity
    lateinit var scannerService: ScannerService
    lateinit var vendorService: VendorService
    lateinit var configuration: Configuration
    lateinit var filtersAdapter: FiltersAdapter

    val context: Context
        get() = mainActivity.applicationContext

    val resources: Resources
        get() = context.resources

    val layoutInflater: LayoutInflater
        get() = mainActivity.layoutInflater

    fun initialize(activity: MainActivity, largeScreen: Boolean) {
        mainActivity = activity
        configuration = Configuration(largeScreen)
        settings = Settings(Repository(mainActivity.applicationContext))
        vendorService = VendorService(mainActivity.resources)
        scannerService = makeScannerService(mainActivity, Handler(), settings)
        filtersAdapter = FiltersAdapter(settings)
    }
}