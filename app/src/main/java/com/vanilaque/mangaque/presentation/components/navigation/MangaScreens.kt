package com.vanilaque.mangaque.presentation.components.navigation

import com.vanilaque.mangaque.util.TITLE_INFO_WEBTOON_ARGUMENT_KEY

//enum class MangaScreens {
//    LibraryScreen,
//    LoginScreen,
//    MainScreen,
//    TitleInfoScreen,
//    TitleReadScreen
//}

sealed class MangaScreens(val route: String) {
    //    object Splash : Screen("splash_screen")
    object LibraryScreen : MangaScreens("welcome_screen")

    object MainScreen : MangaScreens("main_screen")
    object HomeLoginScreene : MangaScreens("home_screen")
    object TitleReadScreen : MangaScreens("title_read/{mangaSlug}/{chapterSlug}") {
        fun passArguments(mangaSlug: String, chapterSlug: String): String {
            return "title_read/$mangaSlug/$chapterSlug"
        }
    }

    object TitleInfoScreen : MangaScreens("title_info/{$TITLE_INFO_WEBTOON_ARGUMENT_KEY}") {
        fun passArg(mangaId: String): String {
            return "title_info/$mangaId"
        }
    }

    object Search : MangaScreens("search_screen")
}