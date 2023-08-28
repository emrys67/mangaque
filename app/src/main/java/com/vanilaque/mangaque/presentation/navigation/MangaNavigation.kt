package com.vanilaque.mangaque.presentation.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vanilaque.mangaque.presentation.components.screens.titleread.TitleReadScreen
import com.vanilaque.mangaque.presentation.screens.main.ExploreScreen
import com.vanilaque.mangaque.presentation.screens.titleinfo.TitleInfoScreen
import com.vanilaque.mangaque.util.READ_TITLE_CHAPTER_ID_ARGUMENT_KEY
import com.vanilaque.mangaque.util.READ_TITLE_CHAPTER_INDEX_ARGUMENT_KEY
import com.vanilaque.mangaque.util.READ_TITLE_WEBTOON_ARGUMENT_KEY
import com.vanilaque.mangaque.util.TITLE_INFO_WEBTOON_ARGUMENT_KEY

@RequiresApi(Build.VERSION_CODES.O)
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
            navArgument(READ_TITLE_WEBTOON_ARGUMENT_KEY) {
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
            navArgument(TITLE_INFO_WEBTOON_ARGUMENT_KEY) {
                type = NavType.StringType
            }
        )) {
            TitleInfoScreen(navController)
        }
//
//        composable(MangaScreens.LibraryScreen.route) {
//            LibraryScreen(navController = navController)
//        }
    }
}