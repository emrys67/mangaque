package com.vanilaque.mangaque.presentation.components.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.vanilaque.mangaque.data.model.Manga
import com.vanilaque.mangaque.presentation.components.EmptyScreen
import com.vanilaque.mangaque.presentation.components.SmallTitle
import com.vanilaque.mangaque.presentation.components.navigation.MangaScreens
import com.vanilaque.mangaque.service.StateManager

@Composable
fun ExploreScreen(
    navController: NavController,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val viewModelState by viewModel.state
    val mangaPaged = viewModel.mangaPaged.collectAsLazyPagingItems()
    val listState = rememberLazyGridState()
    val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
    val hasCalledOnScrolledToEnd by viewModel.hasCalledOnScrolledToEnd

    if (lastVisibleItemIndex >= mangaPaged.itemCount - 1 && !hasCalledOnScrolledToEnd) {
        viewModel.hasCalledOnScrolledToEnd.value = true
        viewModel.fetchMangaFromTheServer()
    }

    DisposableEffect(Unit) {
        StateManager.setShowBottomTopBars(true)
        onDispose {}
    }

    if (viewModelState is ExploreViewModel.ViewModelState.ErrorState) {
        EmptyScreen((viewModelState as ExploreViewModel.ViewModelState.ErrorState).e)
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                state = listState,
                columns = GridCells.Fixed(3), verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp), modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(mangaPaged.itemCount) { mangaItem ->
                    SmallTitle(
                        manga = mangaPaged.get(mangaItem)!!, // TODO: show only if mangaitem != null
                        onClick = {  navigateToTitleInfo(navController, mangaPaged.get(mangaItem)!!.id)  },
                        onLikeClick = { onLikeClick(viewModel, mangaPaged[mangaItem]!!, mangaItem) }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(400.dp))
                }
            }
        }
    }
}

fun onLikeClick(viewModel: ExploreViewModel, manga: Manga, index: Int) {
    viewModel.onLikeMangaClick(manga, index)
}

fun navigateToTitleInfo(navController: NavController, mangaId: String) {
    navController.navigate(MangaScreens.TitleInfoScreen.passArg(mangaId))
}