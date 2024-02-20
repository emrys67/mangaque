package com.vanilaque.mangaque.presentation.screens.titleread

import android.graphics.Bitmap
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vanilaque.mangaque.R
import com.vanilaque.mangaque.data.model.ChapterWithFrames
import com.vanilaque.mangaque.presentation.navigation.MangaScreens
import com.vanilaque.mangaque.service.StateManager
import com.vanilaque.mangaque.theme.MangaPurple
import com.vanilaque.mangaque.theme.Purple200

@Composable
fun TitleReadScreen(navController: NavController, viewModel: TitleReadViewModel = hiltViewModel()) {
    val onBackPressedDispatcher =
        LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val chapterWithFrames by viewModel.chapterWithFrames.collectAsState()
    val chapterBitmaps by viewModel.chapterBitmaps
    val downloadedMode by viewModel.downloadedMode



    DisposableEffect(Unit) {
        StateManager.setShowBottomTopBars(false)
        onDispose {}
    }

    DisposableEffect(navBackStackEntry) {
        val currentRoute = navBackStackEntry?.destination?.route
        if (navController.backQueue.size > 1) {
            if (currentRoute == MangaScreens.TitleReadScreen.route &&
                navController.backQueue[navController.backQueue.size - 2].destination.route == MangaScreens.TitleReadScreen.route
            ) {
                navController.backQueue.removeAt(navController.backQueue.size - 2)
            }
        }
        onDispose {}
    }

    Box {
        if (!downloadedMode) {
            DisplayChapterImagesFromRemote(chapterWithFrames)
        } else {
            DisplayChapterImagesFromLocalStorage(chapterBitmaps)
        }
        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.previous)), style = TextStyle(
                    fontSize = 20.sp,
                    color = Purple200
                )
            ) {
                onPrevClick(
                    navController = navController,
                    viewModel = viewModel,
                    onBackPressedDispatcher = onBackPressedDispatcher
                )
            }


            Spacer(modifier = Modifier.weight(1f))

            ClickableText(
                text = AnnotatedString(stringResource(R.string.next)), style = TextStyle(
                    fontSize = 20.sp,
                    color = Purple200
                )
            ) {
                onNextClick(
                    navController = navController,
                    viewModel = viewModel,
                    onBackPressedDispatcher = onBackPressedDispatcher
                )
            }
        }
    }
}

@Composable
fun DisplayChapterImagesFromLocalStorage(chapterBitmaps: List<Bitmap>) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(items = chapterBitmaps) { item ->
            Image(
                bitmap = item.asImageBitmap(),
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun DisplayChapterImagesFromRemote(chapterWithFrames: ChapterWithFrames?) {
    LazyColumn(Modifier.fillMaxSize()) {
        if (chapterWithFrames != null) {
            items(items = chapterWithFrames.frames) { item ->
                val imageLoaded = remember { mutableStateOf(false) }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.link)
                        .crossfade(true)
                        .build(),
                    contentDescription = "image",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    onSuccess = {
                        imageLoaded.value = true
                    },
                    onError = {
                        imageLoaded.value = false
                    }
                )
                if (!imageLoaded.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(vertical = 64.dp)
                    ) {
                        CircularProgressIndicator(
                            color = MangaPurple,
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

fun onNextClick(
    navController: NavController,
    viewModel: TitleReadViewModel,
    onBackPressedDispatcher: OnBackPressedDispatcher
) {
    val chapterIndexToNavigate = viewModel.chapterIndex + 1

    if (viewModel.checkIfChapterExists(chapterIndexToNavigate)) {
        navController.navigate(
            MangaScreens.TitleReadScreen.passArguments(
                viewModel.mangaId,
                chapterIndexToNavigate,
                viewModel.chapterWithFrames.value!!.chapter.id
            )
        )
    } else {
        onBackPressedDispatcher.onBackPressed()
    }
}

fun onPrevClick(
    navController: NavController,
    viewModel: TitleReadViewModel,
    onBackPressedDispatcher: OnBackPressedDispatcher
) {
    val chapterIndexToNavigate = viewModel.chapterIndex - 1

    if (viewModel.checkIfChapterExists(chapterIndexToNavigate)) {
        navController.navigate(
            MangaScreens.TitleReadScreen.passArguments(
                viewModel.mangaId,
                chapterIndexToNavigate,
                viewModel.chapterWithFrames.value!!.chapter.id
            )
        )
    } else {
        onBackPressedDispatcher.onBackPressed()
    }
}