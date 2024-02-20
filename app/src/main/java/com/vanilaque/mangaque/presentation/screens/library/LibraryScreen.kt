package com.vanilaque.mangaque.presentation.screens.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.vanilaque.mangaque.data.model.Manga
import com.vanilaque.mangaque.presentation.components.ChooseBox
import com.vanilaque.mangaque.presentation.components.ChooseBoxSize
import com.vanilaque.mangaque.presentation.components.FooterPath
import com.vanilaque.mangaque.presentation.components.HorizontalRadioGroup
import com.vanilaque.mangaque.presentation.components.LibraryMangaTitle
import com.vanilaque.mangaque.presentation.screens.main.navigateToTitleInfo
import com.vanilaque.mangaque.service.StateManager
import com.vanilaque.mangaque.theme.MangaPink
import com.vanilaque.mangaque.theme.MangaPurple

@Composable
fun LibraryScreen(navController: NavController, viewModel: LibraryViewModel = hiltViewModel()) {
    val chosenBox by viewModel.chosenBox
    val manga =
        when (chosenBox) {
            ChooseBox.FAVORITES -> viewModel.favoriteManga.collectAsLazyPagingItems()
            else -> viewModel.savedManga.collectAsLazyPagingItems()
        }

    DisposableEffect(Unit) {
        StateManager.setShowBottomTopBars(true)
        StateManager.setFooterPath(FooterPath.LIBRARY)
        onDispose {}
    }

    Column() {
        Spacer(modifier = Modifier.height(8.dp))

        HorizontalRadioGroup(
            options = listOf(ChooseBox.DOWNLOADED, ChooseBox.FAVORITES),
            selected = chosenBox,
            onClick = { viewModel.chosenBox.value = it },
            boxSize = ChooseBoxSize.BIG,
            selectedColor = MangaPurple,
            notSelectedColor = MangaPink,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(manga.itemCount) { index ->
                manga[index]?.let {
                    LibraryMangaTitle(
                        manga = it,
                        { onLikeClick(viewModel, it) },
                        { navigateToTitleInfo(navController, it.id) })
                }
            }
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

fun onLikeClick(viewModel: LibraryViewModel, manga: Manga) {
    viewModel.onLikeMangaClick(manga)
}