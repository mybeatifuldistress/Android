package com.example.myapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {
    private const val PREFS_NAME = "app_prefs"
    private const val KEY_NIGHT_MODE = "night_mode"

    private fun prefs(context: Context): SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isNightFromPrefs(context: Context): Boolean =
        prefs(context).getBoolean(KEY_NIGHT_MODE, false)

    fun saveNightToPrefs(context: Context, night: Boolean) {
        prefs(context).edit().putBoolean(KEY_NIGHT_MODE, night).apply()
    }

    fun apply(night: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (night) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    fun applyFromPrefs(context: Context) = apply(isNightFromPrefs(context))
}