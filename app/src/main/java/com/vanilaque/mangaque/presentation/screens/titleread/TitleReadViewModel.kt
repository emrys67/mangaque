package com.vanilaque.mangaque.presentation.components.screens.titleread

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanilaque.mangaque.data.model.Chapter
import com.vanilaque.mangaque.data.model.ChapterWithFrames
import com.vanilaque.mangaque.data.model.Manga
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

    val _chapterWithFrames: MutableStateFlow<ChapterWithFrames?> = MutableStateFlow(null)
    val chapterWithFrames: StateFlow<ChapterWithFrames?> = _chapterWithFrames
    var chapterIndex: Int = 0
    lateinit var mangaId: String
    lateinit var chapterId: String
    lateinit var chapter: Chapter
    private lateinit var manga: Manga
    init {

        viewModelScope.launch(Dispatchers.IO) {
            mangaId = savedStateHandle.get<String>(READ_TITLE_WEBTOON_ARGUMENT_KEY)!!
            chapterIndex = savedStateHandle.get<Int>(READ_TITLE_CHAPTER_INDEX_ARGUMENT_KEY)!!
            chapterId = savedStateHandle.get<String>(READ_TITLE_CHAPTER_ID_ARGUMENT_KEY)!!
            fetchChapterImages(chapterIndex, mangaId)

            manga = mangaRepository.get(mangaId)
            mangaRepository.update(manga.copy(lastChapterRead = chapterIndex, lastOpenedAt = System.currentTimeMillis(),))


            chapter.let { // TODO:
                _chapterWithFrames.value = framesRepository.getFramesForChapter(it.id)
            }
        }
    }

    suspend fun fetchChapterImages(chapterIndex: Int, mangaId: String){
        Log.e("chapterRepository.getByIndex", "chapterIndex: $chapterIndex, mangaId: $mangaId")
        chapter = chapterRepository.getByIndex(chapterIndex, mangaId)
        val imgs = chapterFrameRepository.fetchFromTheServer(chapter.id)
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