package com.vanilaque.mangaque.presentation.components.screens.titleinfo

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanilaque.mangaque.data.model.Chapter
import com.vanilaque.mangaque.data.model.Manga
import com.vanilaque.mangaque.presentation.components.ChooseBox
import com.vanilaque.mangaque.service.MangaLocalStoreService
import com.vanilaque.mangaque.service.PrefManager
import com.vanilaque.mangaque.util.READ_TITLE_WEBTOON_ARGUMENT_KEY
import com.vanilaque.mangaque.util.TITLE_INFO_WEBTOON_ARGUMENT_KEY
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
    private val mangaRepository: MangaRepository,
    private val mangaLocalStoreService: MangaLocalStoreService,
    private val prefManager: PrefManager,
) : ViewModel() {

    val state: MutableState<ViewModelState> = mutableStateOf(ViewModelState.DefaultState)

    val chosenBox = mutableStateOf(ChooseBox.DESCRIPTION)
    val _chapters: MutableStateFlow<List<Chapter>> = MutableStateFlow(listOf())
    val chapters: StateFlow<List<Chapter>> = _chapters

    val _manga: MutableStateFlow<Manga?> = MutableStateFlow(null)
    val manga: StateFlow<Manga?> = _manga
    val downloadedChapters = mutableStateOf<List<Bitmap>>(listOf())
    val coverBitmap: MutableState<Bitmap?> = mutableStateOf(null)

    init {
        viewModelScope.launch {
            val mangaSlug = savedStateHandle.get<String>(TITLE_INFO_WEBTOON_ARGUMENT_KEY)!!

            //download webtoon cover from storage/server

            initManga(mangaSlug)
            initChapters(mangaSlug)
            coverBitmap.value = mangaLocalStoreService.downloadWebtoonCoverFromServer(manga.value!!.thumb)
        }
    }

    fun getWebtoonFromLocalStorage(mangaItem: Manga) {
        viewModelScope.launch {
            //mangaLocalStoreService.saveWebtoon(mangaItem, chapters.value)
            downloadedChapters.value = mangaLocalStoreService.loadChapterImagesFromFile(
                mangaRepository.getMangaWithChapters(mangaItem.id), 1
            )
            Log.e("", "${downloadedChapters.value}")
        }
    }

    private suspend fun initManga(mangaId: String) {
        _manga.value = mangaRepository.get(mangaId)
    }

    private suspend fun initChapters(mangaId: String) {
        if (areChaptersInDb(mangaId)) {
            _chapters.value =
                chapterRepository.getChaptersForManga(mangaId).chapters.map { it.chapter }
        } else {
            _chapters.value = chapterRepository.fetchFromTheServer(
                mangaId
            )
        }
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