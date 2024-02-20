package com.vanilaque.mangaque.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vanilaque.mangaque.presentation.screens.library.LibraryScreen
import com.vanilaque.mangaque.presentation.screens.main.ExploreScreen
import com.vanilaque.mangaque.presentation.screens.titleinfo.TitleInfoScreen
import com.vanilaque.mangaque.presentation.screens.titleread.TitleReadScreen
import com.vanilaque.mangaque.util.READ_TITLE_CHAPTER_ID_ARGUMENT_KEY
import com.vanilaque.mangaque.util.READ_TITLE_CHAPTER_INDEX_ARGUMENT_KEY
import com.vanilaque.mangaque.util.READ_TITLE_MANGA_ARGUMENT_KEY
import com.vanilaque.mangaque.util.TITLE_INFO_MANGA_ARGUMENT_KEY

@SuppressLint("SuspiciousIndentation")
@Composable
fun MangaNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MangaScreens.MainScreen.route
    ) {
        composable(MangaScreens.MainScreen.route) {
            ExploreScreen(navController = navController)
        }
        composable(route = MangaScreens.TitleReadScreen.route, arguments = listOf(
            navArgument(READ_TITLE_MANGA_ARGUMENT_KEY) {
                type = NavType.StringType
            }, navArgument(READ_TITLE_CHAPTER_INDEX_ARGUMENT_KEY) {
                type = NavType.IntType
            },
            navArgument(READ_TITLE_CHAPTER_ID_ARGUMENT_KEY) {
                type = NavType.StringType
            }
        )) {
            TitleReadScreen(navController)
        }
        composable(route = MangaScreens.TitleInfoScreen.route, arguments = listOf(
            navArgument(TITLE_INFO_MANGA_ARGUMENT_KEY) {
                type = NavType.StringType
            }
        )) {
            TitleInfoScreen(navController)
        }

        composable(MangaScreens.LibraryScreen.route) {
            LibraryScreen(navController = navController)
        }
    }
}