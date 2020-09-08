
package com.sladjan.wifianalyzer.wifi.scanner

import android.os.Handler
import com.sladjan.annotation.OpenClass
import com.sladjan.wifianalyzer.settings.Settings

@OpenClass
internal class PeriodicScan(private val scanner: ScannerService, private val handler: Handler, private val settings: Settings) : Runnable {
    internal var running = false

    fun stop() {
        handler.removeCallbacks(this)
        running = false
    }

    fun start() {
        nextRun(DELAY_INITIAL)
    }

    private fun nextRun(delayInitial: Long) {
        stop()
        handler.postDelayed(this, delayInitial)
        running = true
    }

    override fun run() {
        scanner.update()
        nextRun(settings.scanSpeed() * DELAY_INTERVAL)
    }

    companion object {
        private const val DELAY_INITIAL = 1L
        private const val DELAY_INTERVAL = 1000L
    }
}