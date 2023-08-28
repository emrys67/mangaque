//package com.vanilaque.mangaque.presentation.components.screens.titleread
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.Button
//import androidx.compose.material.ButtonDefaults
//import androidx.compose.material.CircularProgressIndicator
//import androidx.compose.material.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavController
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import com.vanilaque.mangaque.service.StateManager
//import com.vanilaque.mangaque.theme.MangaPurple
//
//@Composable
//fun TitleReadScreen(navController: NavController, viewModel: TitleReadViewModel = hiltViewModel()) {
//    val chapter by viewModel.chapter.collectAsState()
//
//    DisposableEffect(Unit) {
//        StateManager.setShowBottomTopBars(false)
//        onDispose {
//        }
//    }
//
//    LazyColumn() {
//        if (chapter != null) {
//            chapter!!.contentURL?.let {
//                items(items = it) { item ->
//                    var imageLoaded = remember { mutableStateOf(false) }
//                    AsyncImage(
//                        model = ImageRequest.Builder(LocalContext.current)
//                            .data(item)
//                            .crossfade(true)
//                            .build(),
//                        contentDescription = "image", modifier = Modifier
//                            .fillMaxSize(), contentScale = ContentScale.Crop, onSuccess = {
//                            imageLoaded.value = true
//                        }, onError = {
//                            imageLoaded.value = false
//                        }
//                    )
//                    if (!imageLoaded.value) {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .wrapContentHeight()
//                        ) {
//                            CircularProgressIndicator(
//                                color = MangaPurple,
//                                modifier = Modifier
//                                    .size(32.dp)
//                                    .align(Alignment.Center)
//                            )
//                        }
//                    }
//                }
//                item {
//                    Row(Modifier.fillMaxWidth()) {
//                        Button(
//                            onClick = {
//                                onPrevClick(
//                                    navController = navController,
//                                    viewModel = viewModel
//                                )
//                            },
//                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
//                        ) {
//                            Text(
//                                text = "Prev",
//                                modifier = Modifier.wrapContentSize(),
//                                fontSize = 14.sp,
//                                color = Color.White
//                            )
//                        }
//
//                        Spacer(modifier = Modifier.weight(1f))
//
//                        Button(
//                            onClick = {
//                                onNextClick(
//                                    navController = navController,
//                                    viewModel = viewModel
//                                )
//                            },
//                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
//                        ) {
//                            Text(
//                                text = "Next",
//                                modifier = Modifier.wrapContentSize(),
//                                fontSize = 14.sp,
//                                color = Color.White
//                            )
//                        }
//                    }
//                }
//            }
//
//            item {
//                Spacer(modifier = Modifier.height(80.dp))
//            }
//        }
//    }
//}
//
//fun onNextClick(navController: NavController, viewModel: TitleReadViewModel) {
////    navController.navigate(
////        MangaScreens.TitleReadScreen.passArguments(
////            viewModel.webtoonSlug.value!!,
////            viewModel.getNextChapterSlug()!!
////        )
////    )
//}
//
//fun onPrevClick(navController: NavController, viewModel: TitleReadViewModel) {
////    navController.navigate(
////        MangaScreens.TitleReadScreen.passArguments(
////            viewModel.webtoonSlug.value!!,
////            viewModel.getPrivChapterSlug()!!
////        )
////    )
//}