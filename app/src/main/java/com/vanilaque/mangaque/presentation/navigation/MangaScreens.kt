package com.vanilaque.mangaque.presentation.navigation

import com.vanilaque.mangaque.util.TITLE_INFO_MANGA_ARGUMENT_KEY

sealed class MangaScreens(val route: String) {
    object LibraryScreen : MangaScreens("welcome_screen")
    object MainScreen : MangaScreens("main_screen")
    object TitleReadScreen : MangaScreens("title_read/{manga_id}/{chapter_index}/{chapter_id}") {
        fun passArguments(mangaId: String, chapterIndex: Int, chapterId: String): String {
            return "title_read/$mangaId/$chapterIndex/$chapterId"
        }
    }

    object TitleInfoScreen : MangaScreens("title_info/{$TITLE_INFO_MANGA_ARGUMENT_KEY}") {
        fun passArg(mangaId: String): String {
            return "title_info/$mangaId"
        }
    }
}