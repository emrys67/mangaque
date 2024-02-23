package com.vanilaque.mangaque.presentation.screens.titleread

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanilaque.mangaque.data.model.Chapter
import com.vanilaque.mangaque.data.model.ChapterWithFrames
import com.vanilaque.mangaque.data.model.MangaWithChapters
import com.vanilaque.mangaque.data.repository.ChapterFrameRepository
import com.vanilaque.mangaque.data.repository.ChapterRepository
import com.vanilaque.mangaque.data.repository.impl.MangaRepositoryImpl
import com.vanilaque.mangaque.service.MangaLocalStoreService
import com.vanilaque.mangaque.service.PrefManager
import com.vanilaque.mangaque.util.READ_TITLE_CHAPTER_ID_ARGUMENT_KEY
import com.vanilaque.mangaque.util.READ_TITLE_CHAPTER_INDEX_ARGUMENT_KEY
import com.vanilaque.mangaque.util.READ_TITLE_MANGA_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TitleReadViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val mangaRepository: MangaRepositoryImpl,
    private val mangaLocalStoreService: MangaLocalStoreService,
    private val chapterRepository: ChapterRepository,
    private val framesRepository: ChapterFrameRepository,
    private val chapterFrameRepository: ChapterFrameRepository,
    val prefManager: PrefManager
) : ViewModel() {

    private val _chapterWithFrames: MutableStateFlow<ChapterWithFrames?> = MutableStateFlow(null)
    val chapterWithFrames: StateFlow<ChapterWithFrames?> = _chapterWithFrames
    val chapterBitmaps: MutableState<List<Bitmap>> = mutableStateOf(listOf())
    var chapterIndex: Int = 0
    val downloadedMode = mutableStateOf(false)
    lateinit var mangaId: String
    lateinit var chapterId: String
    lateinit var chapter: Chapter
    private lateinit var mangaWithChapters: MangaWithChapters

    init {
        viewModelScope.launch(Dispatchers.IO) {
            initArgumentsValues(savedStateHandle)
            fetchMangaParamsFromLocalRepo()
            fetchChapterFrames()
            updateMangaInterructionInfo()
            downloadedMode.value = mangaWithChapters.manga.downloaded
        }
    }

    fun checkIfChapterExists(chapterIndex: Int): Boolean {
        return mangaWithChapters.chapters.firstOrNull { it.chapter.index == chapterIndex } != null
    }

    private fun initArgumentsValues(savedStateHandle: SavedStateHandle) {
        mangaId = savedStateHandle.get<String>(READ_TITLE_MANGA_ARGUMENT_KEY)!!
        chapterIndex = savedStateHandle.get<Int>(READ_TITLE_CHAPTER_INDEX_ARGUMENT_KEY)!!
        chapterId = savedStateHandle.get<String>(READ_TITLE_CHAPTER_ID_ARGUMENT_KEY)!!
    }

    private suspend fun fetchMangaParamsFromLocalRepo() {
        chapter = chapterRepository.getByIndex(chapterIndex, mangaId)
        mangaWithChapters = mangaRepository.getMangaWithChapters(mangaId)
        _chapterWithFrames.value = framesRepository.getFramesForChapter(chapter.id)
    }

    private suspend fun fetchChapterFrames() {
        if (mangaWithChapters.manga.downloaded) {
            fetchChapterFramesLocalStorage()
        } else {
            fetchChapterFramesRemote()
        }
    }

    private suspend fun fetchChapterFramesRemote() {
        try {
            val images = chapterFrameRepository.fetchFromTheServer(chapter.id)
            framesRepository.insertAll(images)
        }
        catch (e: Exception){
            Timber.e("fetchChapterFramesRemote $e")
            // TODO: handle (redirect back or show retry dialog)
        }
    }

    private fun fetchChapterFramesLocalStorage() {
        if (chapterWithFrames.value != null) {
            try {
                chapterBitmaps.value =
                    mangaLocalStoreService.loadChapterImagesFromFile(chapterWithFrames.value!!)
            }
            catch (e: Exception){
                Timber.e("fetchChapterFramesLocalStorage $e")
                // TODO: handle (redirect back or show retry dialog)
            }
        }
    }

    private suspend fun updateMangaInterructionInfo() {
        mangaRepository.update(
            mangaWithChapters.manga.copy(
                lastChapterRead = chapterIndex,
                lastOpenedAt = System.currentTimeMillis()
            )
        )
    }
}