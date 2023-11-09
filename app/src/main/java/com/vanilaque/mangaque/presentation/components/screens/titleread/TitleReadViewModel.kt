//package com.vanilaque.mangaque.presentation.components.screens.titleread
//
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.vanilaque.mangaque.data.model.Chapter
//import com.vanilaque.mangaque.service.PrefManager
//import com.vanilaque.mangaque.util.READ_TITLE_CHAPTER_ARGUMENT_KEY
//import com.vanilaque.mangaque.util.READ_TITLE_WEBTOON_ARGUMENT_KEY
//import com.vanilaque.mangareader.data.repository.impl.ChapterRepositoryImpl
//import com.vanilaque.mangareader.data.repository.impl.MangaRepositoryImpl
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class TitleReadViewModel @Inject constructor(
//    savedStateHandle: SavedStateHandle, val mangaRepository: MangaRepositoryImpl,
//    val chapterRepositoryImpl: ChapterRepositoryImpl, val prefManager: PrefManager
//) : ViewModel() {
//
//    val _chapter: MutableStateFlow<Chapter?> = MutableStateFlow(null)
//    val chapter: StateFlow<Chapter?> = _chapter
//
//    val _webtoonSlug: MutableStateFlow<String?> = MutableStateFlow(null)
//    val webtoonSlug: StateFlow<String?> = _webtoonSlug
//
//    init {
//
//        viewModelScope.launch(Dispatchers.IO) {
//            _webtoonSlug.value = savedStateHandle.get<String>(READ_TITLE_WEBTOON_ARGUMENT_KEY)
//            val chapterSlug = savedStateHandle.get<String>(READ_TITLE_CHAPTER_ARGUMENT_KEY)
//
//            //_webtoon.value = mangaSlug?.let { webtoonRepositoryImpl.getWebtoonBySlugFromServer(prefManager.chosenProvider!!, mangaSlug) }
////            _chapter.value = chapterRepositoryImpl.getChapterBySlugFromServer(
////                prefManager.chosenProvider!!,
////                webtoonSlug.value!!,
////                chapterSlug!!
////            )
//        }
//    }
//
////    fun getNextChapterSlug(): String? {
////        return chapter.value!!.id
////    }
////
////    fun getPrivChapterSlug(): String? {
////        return chapter.value!!.privChapterSlug
////    }
//
//    //if this chapter is in db - fetch it from db
//    //if not - fetch from the server
//
//}