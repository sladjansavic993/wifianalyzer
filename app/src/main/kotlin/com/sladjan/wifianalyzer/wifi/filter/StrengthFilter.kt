
package com.sladjan.wifianalyzer.wifi.filter

import android.app.Dialog
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.wifi.filter.adapter.StrengthAdapter
import com.sladjan.wifianalyzer.wifi.model.Strength

internal class StrengthFilter(strengthAdapter: StrengthAdapter, dialog: Dialog) :
        EnumFilter<Strength, StrengthAdapter>(
                mapOf(
                        Strength.ZERO to R.id.filterStrength0,
                        Strength.ONE to R.id.filterStrength1,
                        Strength.TWO to R.id.filterStrength2,
                        Strength.THREE to R.id.filterStrength3,
                        Strength.FOUR to R.id.filterStrength4
                ),
                strengthAdapter,
                dialog,
                R.id.filterStrength
        )