package com.rishik.sahayak.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val EMAIL = "email"
private const val USERNAME = "username"

object SavedPreference {
    private fun getSharedPreference(ctx: Context?): SharedPreferences? {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    private fun editor(context: Context, const: String, string: String) {
        getSharedPreference(
            context
        )?.edit()?.putString(const, string)?.apply()
    }

    fun getEmail(context: Context) = getSharedPreference(
        context
    )?.getString(EMAIL, "")

    fun setEmail(context: Context, email: String) {
        editor(
            context,
            EMAIL,
            email
        )
    }

    fun setUsername(context: Context, username: String) {
        editor(
            context,
            USERNAME,
            username
        )
    }

    fun getUsername(context: Context) = getSharedPreference(
        context
    )?.getString(USERNAME, "")
}