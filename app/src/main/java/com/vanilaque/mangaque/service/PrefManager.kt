package com.vanilaque.mangaque.service

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class PrefManager @Inject constructor(context: Context) {
    companion object {
        const val PREF_NAME = "mangaque.prefs"
        const val BEEN_APP_COMPLETELY_CLOSED = "been_app_completely_closed"
        const val MANGA_FEED_PAGE = "manga_feed_page"
    }

    private val shared: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var beenAppCompletelyClosed: Boolean
        get() = shared.getBoolean(BEEN_APP_COMPLETELY_CLOSED, false)
        set(value) = shared.edit {
            putBoolean(BEEN_APP_COMPLETELY_CLOSED, value)
        }

    var mangaFeedPage: Int
        get() = shared.getInt(MANGA_FEED_PAGE, 1)
        set(value) = shared.edit {
            putInt(MANGA_FEED_PAGE, value)
        }
}