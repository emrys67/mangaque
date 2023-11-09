package com.vanilaque.mangaque.presentation.components.screens.titleread

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vanilaque.mangaque.presentation.navigation.MangaScreens
import com.vanilaque.mangaque.service.StateManager
import com.vanilaque.mangaque.theme.MangaPurple
import com.vanilaque.mangaque.theme.Purple200

@Composable
fun TitleReadScreen(navController: NavController, viewModel: TitleReadViewModel = hiltViewModel()) {
    val chapter by viewModel.chapterWithFrames.collectAsState()

    BackHandler {
        navController.navigate(MangaScreens.TitleInfoScreen.passArg(viewModel.mangaId))
    }

    DisposableEffect(Unit) {
        StateManager.setShowBottomTopBars(false)
        onDispose {
        }
    }

    Box {
        LazyColumn(Modifier.fillMaxSize()) {
            if (chapter != null) {
                chapter!!.frames.let {
                    items(items = it) { item ->
                        var imageLoaded = remember { mutableStateOf(false) }
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(item.link)
                                .crossfade(true)
                                .build(),
                            contentDescription = "image", modifier = Modifier
                                .fillMaxSize(), contentScale = ContentScale.Crop, onSuccess = {
                                imageLoaded.value = true
                            }, onError = {
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
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            ClickableText(
                text = AnnotatedString("Prev"), style = TextStyle(
                    fontSize = 20.sp,
                    color = Purple200
                )
            ) {
                onPrevClick(
                    navController = navController,
                    viewModel = viewModel
                )
            }


            Spacer(modifier = Modifier.weight(1f))

            ClickableText(
                text = AnnotatedString("Next"), style = TextStyle(
                    fontSize = 20.sp,
                    color = Purple200
                )
            ) {
                onNextClick(
                    navController = navController,
                    viewModel = viewModel
                )
            }

//            Button(
//                modifier = Modifier.weight(1f),
//                onClick = {
//                    onNextClick(
//                        navController = navController,
//                        viewModel = viewModel
//                    )
//                },
//                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
//            ) {
//                Text(
//                    text = "Next",
//                    modifier = Modifier.wrapContentSize(),
//                    fontSize = 14.sp,
//                    color = Color.White
//                )
//            }
        }
    }
}

fun onNextClick(navController: NavController, viewModel: TitleReadViewModel) { // TODO:  
    navController.navigate(
        MangaScreens.TitleReadScreen.passArguments(
            viewModel.mangaId,
            viewModel.chapterIndex + 1,
            viewModel.chapterWithFrames.value!!.chapter.id
        )
    )
}

fun onPrevClick(navController: NavController, viewModel: TitleReadViewModel) { // TODO:
    navController.navigate(
        MangaScreens.TitleReadScreen.passArguments(
            viewModel.mangaId,
            viewModel.chapterIndex - 1,
            viewModel.chapterWithFrames.value!!.chapter.id
        )
    )
}