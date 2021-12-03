package com.rishik.sahayak.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val EMAIL = "email"
private const val USERNAME = "username"
private const val USERID = "userId"
private const val USERTYPE = "userType"


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

    fun setId(context: Context, userId: String) {
        editor(
            context,
            USERID,
            userId
        )
    }

    fun getId(context: Context) = getSharedPreference(
        context
    )?.getString(USERID, "")

    fun setUserType(context: Context, userType: String) {
        editor(
            context,
            USERTYPE,
            userType
        )
    }

    fun getUserType(context: Context) = getSharedPreference(
        context
    )?.getString(USERTYPE, "")
}