package com.vanilaque.mangaque.presentation.components.screens.titleread

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanilaque.mangaque.data.model.ChapterWithFrames
import com.vanilaque.mangaque.service.PrefManager
import com.vanilaque.mangaque.util.READ_TITLE_CHAPTER_ID_ARGUMENT_KEY
import com.vanilaque.mangaque.util.READ_TITLE_CHAPTER_INDEX_ARGUMENT_KEY
import com.vanilaque.mangaque.util.READ_TITLE_WEBTOON_ARGUMENT_KEY
import com.vanilaque.mangareader.data.repository.ChapterFrameRepository
import com.vanilaque.mangareader.data.repository.ChapterRepository
import com.vanilaque.mangareader.data.repository.impl.MangaRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TitleReadViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val mangaRepository: MangaRepositoryImpl,
    val chapterRepository: ChapterRepository,
    val framesRepository: ChapterFrameRepository,
    val chapterFrameRepository: ChapterFrameRepository,
    val prefManager: PrefManager
) : ViewModel() {

    val _chapter: MutableStateFlow<ChapterWithFrames?> = MutableStateFlow(null)
    val chapter: StateFlow<ChapterWithFrames?> = _chapter
    var chapterIndex: Int = 0
    lateinit var mangaId: String
    lateinit var chapterId: String
    init {

        viewModelScope.launch(Dispatchers.IO) {
            mangaId = savedStateHandle.get<String>(READ_TITLE_WEBTOON_ARGUMENT_KEY)!!
            chapterIndex = savedStateHandle.get<Int>(READ_TITLE_CHAPTER_INDEX_ARGUMENT_KEY)!!
            chapterId = savedStateHandle.get<String>(READ_TITLE_CHAPTER_ID_ARGUMENT_KEY)!!
            fetchChapterImages(chapterId)


            chapterId.let {
                _chapter.value = framesRepository.getFramesForChapter(it)
            }
        }
    }

    suspend fun fetchChapterImages(chapterId: String){
        val imgs = chapterFrameRepository.fetchFromTheServer(chapterId)
        framesRepository.insertAll(imgs)
    }

//    fun getNextChapterSlug(): String? {
//        return chapter.value!!.id
//    }
//
//    fun getPrivChapterSlug(): String? {
//        return chapter.value!!.privChapterSlug
//    }

//if this chapter is in db - fetch it from db
//if not - fetch from the server

}