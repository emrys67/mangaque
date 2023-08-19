package com.vanilaque.mangaque.service

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PrefManager @Inject constructor(context: Context) {
    companion object {
        const val PREF_NAME = "mangaque.prefs"
        const val FOOTER_PATH = "footer_path"
        const val CHOSEN_PROVIDER = "chosen_provider"
    }

    private val shared: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

//    var footerPath: FooterPath?
//        get() = Gson().fromJson(
//            shared.getString(FOOTER_PATH, Gson().toJson(FooterPath.CATALOG)),
//            FooterPath::class.java
//        )
//        set(value) = shared.edit {
//            putString(FOOTER_PATH, Gson().toJson(value))
//        }
}