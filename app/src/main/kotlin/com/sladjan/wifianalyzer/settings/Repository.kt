
package com.sladjan.wifianalyzer.settings

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.preference.PreferenceManager
import com.sladjan.annotation.OpenClass
import com.sladjan.wifianalyzer.R

inline fun SharedPreferences.edit(func: SharedPreferences.Editor.() -> Unit) {
    val editor: SharedPreferences.Editor = edit()
    editor.func()
    editor.apply()
}

@OpenClass
class Repository(private val context: Context) {

    fun initializeDefaultValues(): Unit = defaultValues(context, R.xml.settings, false)

    fun registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener: OnSharedPreferenceChangeListener): Unit =
            sharedPreferences().registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)

    fun save(key: Int, value: Int): Unit = save(key, value.toString())

    fun save(key: Int, value: String): Unit = sharedPreferences().edit { putString(context.getString(key), value) }

    fun stringAsInteger(key: Int, defaultValue: Int): Int =
            try {
                string(key, defaultValue.toString()).toInt()
            } catch (e: Exception) {
                defaultValue
            }

    fun string(key: Int, defaultValue: String): String {
        val keyValue: String = context.getString(key)
        return try {
            sharedPreferences().getString(keyValue, defaultValue) ?: defaultValue
        } catch (e: Exception) {
            sharedPreferences().edit { putString(keyValue, defaultValue) }
            defaultValue
        }
    }

    fun boolean(key: Int, defaultValue: Boolean): Boolean {
        val keyValue: String = context.getString(key)
        return try {
            sharedPreferences().getBoolean(keyValue, defaultValue)
        } catch (e: Exception) {
            sharedPreferences().edit { putBoolean(keyValue, defaultValue) }
            defaultValue
        }
    }

    fun resourceBoolean(key: Int): Boolean = context.resources.getBoolean(key)

    fun integer(key: Int, defaultValue: Int): Int {
        val keyValue: String = context.getString(key)
        return try {
            sharedPreferences().getInt(keyValue, defaultValue)
        } catch (e: Exception) {
            sharedPreferences().edit { putString(keyValue, defaultValue.toString()) }
            defaultValue
        }
    }

    fun stringSet(key: Int, defaultValues: Set<String>): Set<String> {
        val keyValue: String = context.getString(key)
        return try {
            sharedPreferences().getStringSet(keyValue, defaultValues)!!
        } catch (e: Exception) {
            sharedPreferences().edit { putStringSet(keyValue, defaultValues) }
            defaultValues
        }
    }

    fun saveStringSet(key: Int, values: Set<String>): Unit =
            sharedPreferences().edit { putStringSet(context.getString(key), values) }

    fun defaultValues(context: Context?, resId: Int, readAgain: Boolean): Unit =
            PreferenceManager.setDefaultValues(context, resId, readAgain)

    fun defaultSharedPreferences(context: Context?): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

    private fun sharedPreferences(): SharedPreferences = defaultSharedPreferences(context)

}