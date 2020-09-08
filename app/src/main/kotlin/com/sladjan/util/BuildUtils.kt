
package com.sladjan.util

import android.os.Build

fun buildMinVersionQ(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

fun buildMinVersionP(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

fun buildVersionP(): Boolean = Build.VERSION.SDK_INT == Build.VERSION_CODES.P

fun buildMinVersionN(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

fun buildMinVersionM(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

fun buildMinVersionL(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
