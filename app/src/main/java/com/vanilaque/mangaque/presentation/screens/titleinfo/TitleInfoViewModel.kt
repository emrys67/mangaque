package com.vanilaque.mangaque.presentation.screens.titleinfo

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanilaque.mangaque.data.model.Chapter
import com.vanilaque.mangaque.data.model.Manga
import com.vanilaque.mangaque.data.model.MangaWithChapters
import com.vanilaque.mangaque.presentation.components.ChooseBox
import com.vanilaque.mangaque.service.MangaLocalStoreService
import com.vanilaque.mangaque.service.PrefManager
import com.vanilaque.mangaque.usecase.ChapterUseCase
import com.vanilaque.mangaque.util.TITLE_INFO_WEBTOON_ARGUMENT_KEY
import com.vanilaque.mangareader.data.repository.ChapterFrameRepository
import com.vanilaque.mangareader.data.repository.ChapterRepository
import com.vanilaque.mangareader.data.repository.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TitleInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val chapterRepository: ChapterRepository,
    private val chapterFrameRepository: ChapterFrameRepository,
    private val mangaRepository: MangaRepository,
    private val mangaLocalStoreService: MangaLocalStoreService,
    private val chapterUseCase: ChapterUseCase,
    private val prefManager: PrefManager,
) : ViewModel() {

    val state: MutableState<ViewModelState> = mutableStateOf(ViewModelState.DefaultState)

    val chosenBox = mutableStateOf(ChooseBox.DESCRIPTION)
    val _chapters: MutableStateFlow<List<Chapter>> = MutableStateFlow(listOf())
    val chapters: StateFlow<List<Chapter>> = _chapters

    val _manga: MutableStateFlow<MangaWithChapters?> = MutableStateFlow(null)
    val manga: StateFlow<MangaWithChapters?> = _manga
    val downloadedChapters = mutableStateOf<List<Bitmap>>(listOf())
    val coverBitmap: MutableState<Bitmap?> = mutableStateOf(null)

    init {
        viewModelScope.launch {
            val mangaSlug = savedStateHandle.get<String>(TITLE_INFO_WEBTOON_ARGUMENT_KEY)!!

            //download webtoon cover from storage/server

            initManga(mangaSlug)
            coverBitmap.value =
                mangaLocalStoreService.downloadMangaCoverFromServer(manga.value!!.manga.thumb)
            initChapters(mangaSlug)
        }
    }

    fun getWebtoonFromLocalStorage(mangaItem: Manga) {
        viewModelScope.launch {
            chapterUseCase.syncChapters(mangaItem.id)
            val mangaWithChapters = mangaRepository.getMangaWithChapters(mangaItem.id)
            mangaLocalStoreService.saveManga(mangaWithChapters)
            downloadedChapters.value = mangaLocalStoreService.loadChapterImagesFromFile(
                mangaRepository.getMangaWithChapters(mangaItem.id), 1
            )
            Log.e("", "${downloadedChapters.value}")
        }
    }

    private suspend fun initManga(mangaId: String) {
        _manga.value = mangaRepository.getMangaWithChapters(mangaId)
    }

    private suspend fun initChapters(mangaId: String) {
        if (!areChaptersInDb(mangaId)) {
            chapterUseCase.syncChapters(mangaId)
        }

        _chapters.value =
            chapterRepository.getChaptersForManga(mangaId).chapters.map { it.chapter }
    }

    private suspend fun isMangaDownloaded(mangaId: String): Boolean {
        return try {
            mangaRepository.get(mangaId).downloaded
        } catch (e: Exception) {
            false
        }
    }


    private suspend fun areChaptersInDb(mangaId: String): Boolean {
        return try {
            chapterRepository.getChaptersForManga(mangaId).chapters.isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }

    open class ViewModelState() {
        object RequirePermissionsState : ViewModelState()
        object DefaultState : ViewModelState()
    }
}