package com.vanilaque.mangaque.service

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.vanilaque.mangaque.presentation.components.FooterPath
import javax.inject.Inject

class PrefManager @Inject constructor(context: Context) {
    companion object {
        const val PREF_NAME = "mangaque.prefs"
        const val FOOTER_PATH = "footer_path"
        const val BEEN_APP_COMPLETELY_CLOSED = "been_app_completely_closed"
        const val MANGA_FEED_PAGE = "manga_feed_page"
    }

    private val shared: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var footerPath: FooterPath?
        get() = Gson().fromJson(
            shared.getString(FOOTER_PATH, Gson().toJson(FooterPath.CATALOG)),
            FooterPath::class.java
        )
        set(value) = shared.edit {
            putString(FOOTER_PATH, Gson().toJson(value))
        }

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