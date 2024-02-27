@file:OptIn(ExperimentalMaterialApi::class)

package com.vanilaque.mangaque.presentation.screens.titleinfo

import android.Manifest
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.vanilaque.mangaque.R
import com.vanilaque.mangaque.data.model.Chapter
import com.vanilaque.mangaque.data.model.Manga
import com.vanilaque.mangaque.data.model.MangaWithChapters
import com.vanilaque.mangaque.presentation.components.ChooseBox
import com.vanilaque.mangaque.presentation.components.ChooseBoxSize
import com.vanilaque.mangaque.presentation.components.HorizontalRadioGroup
import com.vanilaque.mangaque.presentation.components.MangaSaveInProgressDialog
import com.vanilaque.mangaque.presentation.navigation.MangaScreens
import com.vanilaque.mangaque.service.StateManager
import com.vanilaque.mangaque.theme.EXTRA_SMALL_PADDING
import com.vanilaque.mangaque.theme.MEDIUM_PADDING
import com.vanilaque.mangaque.theme.MICRO_PADDING
import com.vanilaque.mangaque.theme.MangaPink
import com.vanilaque.mangaque.theme.MangaPurple

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TitleInfoScreen(navController: NavController, viewModel: TitleInfoViewModel = hiltViewModel()) {
    val chapters by viewModel.chapters.collectAsState()
    val manga by viewModel.manga.collectAsState()
    val state by viewModel.state
    val chosenBox by viewModel.chosenBox
    val coverBitmap by viewModel.coverBitmap
    val permissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
        )
    )

    LaunchedEffect(state) {
        if (state == TitleInfoViewModel.ViewModelState.RequirePermissionsState) {
            permissionsState.launchMultiplePermissionRequest()
            viewModel.state.value = TitleInfoViewModel.ViewModelState.DefaultState
        }
    }

    DisposableEffect(Unit) {
        StateManager.setShowBottomTopBars(false)
        onDispose {
        }
    }

    DisposableEffect(Unit) {
        navController.backQueue.removeAll { it.destination.route == MangaScreens.TitleReadScreen.route }
        onDispose {}
    }

    Box {
        MangaSaveInProgressDialog(
            state = state,
            onClose = { viewModel.state.value = TitleInfoViewModel.ViewModelState.DefaultState })
        Column(
            Modifier
                .fillMaxSize()
                .then(
                    if (chosenBox == ChooseBox.DESCRIPTION) Modifier.verticalScroll(
                        rememberScrollState()
                    ) else Modifier
                )
                .padding(EXTRA_SMALL_PADDING)
        ) {

            manga?.let {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MEDIUM_PADDING),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = it.manga.title,
                        modifier = Modifier
                            .weight(1f),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                    Image(
                        painter = painterResource(id = R.drawable.downloaded),
                        contentDescription = "download",
                        colorFilter = ColorFilter.tint(Color.LightGray),
                        modifier = Modifier
                            .size(48.dp)
                            .clickable {
                                onDownloadClick(
                                    viewModel,
                                    it.manga,
                                    permissionsState.allPermissionsGranted
                                )
                            }
                            .align(Alignment.Bottom)
                            .padding(end = MEDIUM_PADDING)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            manga?.let {
                LazyRow(
                    modifier = Modifier
                        .height(24.dp)
                        .fillMaxWidth()
                        .padding(horizontal = MEDIUM_PADDING),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    it.manga.genres.let { genres ->
                        items(genres) { genre ->
                            GenreBox(name = genre)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                coverBitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "image",
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                HorizontalRadioGroup(
                    options = listOf(ChooseBox.DESCRIPTION, ChooseBox.CHAPTERS),
                    selected = chosenBox,
                    onClick = { viewModel.chosenBox.value = it },
                    boxSize = ChooseBoxSize.BIG,
                    selectedColor = MangaPurple,
                    notSelectedColor = MangaPink,
                    modifier = Modifier.align(CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (chosenBox == ChooseBox.DESCRIPTION) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(EXTRA_SMALL_PADDING),
                        shape = RoundedCornerShape(8.dp), backgroundColor = Color.White,
                        elevation = 6.dp,
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(
                                    vertical = MEDIUM_PADDING,
                                    horizontal = EXTRA_SMALL_PADDING
                                ),
                            text = manga!!.manga.summary,
                            textAlign = TextAlign.Start,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    }
                } else {
                    if (chapters.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            CircularProgressIndicator(
                                color = MangaPurple,
                                modifier = Modifier
                                    .size(64.dp)
                                    .align(Center)
                            )
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(32.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(
                                    horizontal = MEDIUM_PADDING,
                                    vertical = EXTRA_SMALL_PADDING
                                )
                        ) {
                            items(chapters) {
                                ChapterItem(chapter = it, mangaItem = manga!!, navController)
                            }
                            item {
                                Spacer(modifier = Modifier.height(100.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GenreBox(name: String) {
    Box(
        modifier = Modifier
            .height(24.dp)
            .wrapContentWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(color = Color.LightGray)
            .padding(horizontal = MICRO_PADDING),
        contentAlignment = Center
    ) {
        Text(
            text = name,
            modifier = Modifier.wrapContentSize(),
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChapterItem(chapter: Chapter, mangaItem: MangaWithChapters, navController: NavController) {
    Card(modifier = Modifier
        .height(68.dp)
        .width(68.dp),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        onClick = {
            navigateToChapter(navController, mangaItem = mangaItem, chapter = chapter)
        }) {
        Box(Modifier.fillMaxSize()) {
            Text(
                text = chapter.title,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Center)
            )
        }
    }
}

fun onDownloadClick(viewModel: TitleInfoViewModel, manga: Manga, permissionsGranted: Boolean) {
    if (permissionsGranted) {
        viewModel.saveMangaInLocalStorage(manga)
    } else {
        viewModel.state.value = TitleInfoViewModel.ViewModelState.RequirePermissionsState
    }
}

fun navigateToChapter(
    navController: NavController,
    mangaItem: MangaWithChapters,
    chapter: Chapter
) {
    navController.navigate(
        MangaScreens.TitleReadScreen.passArguments(
            mangaItem.manga.id,
            chapter.index,
            chapter.id
        )
    )
}
