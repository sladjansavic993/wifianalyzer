
package com.sladjan.wifianalyzer.wifi.filter

import android.app.Dialog
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.wifi.filter.adapter.SecurityAdapter
import com.sladjan.wifianalyzer.wifi.model.Security

internal class SecurityFilter(securityAdapter: SecurityAdapter, dialog: Dialog) :
        EnumFilter<Security, SecurityAdapter>(
                mapOf(
                        Security.NONE to R.id.filterSecurityNone,
                        Security.WPS to R.id.filterSecurityWPS,
                        Security.WEP to R.id.filterSecurityWEP,
                        Security.WPA to R.id.filterSecurityWPA,
                        Security.WPA2 to R.id.filterSecurityWPA2,
                        Security.WPA3 to R.id.filterSecurityWPA3
                ),
                securityAdapter,
                dialog,
                R.id.filterSecurity
        )
