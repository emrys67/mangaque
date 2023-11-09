//package com.vanilaque.mangaque.presentation.components.screens.library
//
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavController
//import com.vanilaque.mangaque.presentation.components.ChooseBox
//import com.vanilaque.mangaque.presentation.components.ChooseBoxSize
//import com.vanilaque.mangaque.presentation.components.HorizontalRadioGroup
//import com.vanilaque.mangaque.presentation.components.LibraryMangaTitle
//import com.vanilaque.mangaque.theme.MangaPink
//import com.vanilaque.mangaque.theme.MangaPurple
//import com.vanilaque.mangareader.data.model.Webtoon
//import com.vanilaque.mangareader.presentation.components.*
//import com.vanilaque.mangareader.ui.theme.MangaPink
//import com.vanilaque.mangareader.ui.theme.MangaPurple
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun LibraryScreen(navController: NavController, viewModel: LibraryViewModel = hiltViewModel()) {
//    val chosenBox by viewModel.chosenBox
//    val webtoons by viewModel.webtoons
//    Column() {
//        Spacer(modifier = Modifier.height(8.dp))
//        //downloaded - favorites
//        HorizontalRadioGroup(
//            options = listOf(ChooseBox.DOWNLOADED, ChooseBox.FAVORITES),
//            selected = chosenBox,
//            onClick = { viewModel.chosenBox.value = it },
//            boxSize = ChooseBoxSize.BIG,
//            selectedColor = MangaPurple,
//            notSelectedColor = MangaPink,
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        LazyColumn(modifier = Modifier.fillMaxSize()) {
//            itemsIndexed(webtoons) { index, webtoon ->
//                LibraryMangaTitle(
//                    webtoon = webtoon,
//                    { onLikeClick(viewModel, webtoon, index) },
//                    { navigateToTitleInfo(navController, webtoon) })
//            }
//            item {
//                Spacer(modifier = Modifier.height(100.dp))
//            }
//        }
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//fun onLikeClick(viewModel: LibraryViewModel, webtoon: Webtoon, index: Int) {
//    viewModel.onLikeWebtoonClick(webtoon, index)
//}