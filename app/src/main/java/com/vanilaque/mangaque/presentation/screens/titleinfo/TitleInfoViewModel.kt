package com.vanilaque.mangaque.presentation.screens.titleinfo

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanilaque.mangaque.data.model.Chapter
import com.vanilaque.mangaque.data.model.Manga
import com.vanilaque.mangaque.data.model.MangaWithChapters
import com.vanilaque.mangaque.data.repository.ChapterRepository
import com.vanilaque.mangaque.data.repository.MangaRepository
import com.vanilaque.mangaque.presentation.components.ChooseBox
import com.vanilaque.mangaque.service.MangaLocalStoreService
import com.vanilaque.mangaque.usecase.ChapterUseCase
import com.vanilaque.mangaque.util.TITLE_INFO_MANGA_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class TitleInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val chapterRepository: ChapterRepository,
    private val mangaRepository: MangaRepository,
    private val mangaLocalStoreService: MangaLocalStoreService,
    private val chapterUseCase: ChapterUseCase,
) : ViewModel() {

    val state: MutableState<ViewModelState> = mutableStateOf(ViewModelState.DefaultState)

    val chosenBox = mutableStateOf(ChooseBox.DESCRIPTION)
    private val _chapters: MutableStateFlow<List<Chapter>> = MutableStateFlow(listOf())
    val chapters: StateFlow<List<Chapter>> = _chapters

    private val _manga: MutableStateFlow<MangaWithChapters?> = MutableStateFlow(null)
    val manga: StateFlow<MangaWithChapters?> = _manga
    val coverBitmap: MutableState<Bitmap?> = mutableStateOf(null)

    init {
        viewModelScope.launch {
            val mangaSlug = savedStateHandle.get<String>(TITLE_INFO_MANGA_ARGUMENT_KEY)!!

            fetchManga(mangaSlug)
            initChapters(mangaSlug)
        }
    }

    fun saveMangaInLocalStorage(mangaItem: Manga) {
        if (!mangaItem.downloaded && state.value !is ViewModelState.DownloadingState.MangaIsSaving)
            viewModelScope.launch {
                try {
                    state.value = ViewModelState.DownloadingState.MangaIsSaving
                    chapterUseCase.syncChapters(mangaItem.id)
                    val mangaWithChapters = mangaRepository.getMangaWithChapters(mangaItem.id)
                    mangaLocalStoreService.saveManga(mangaWithChapters)
                    state.value = ViewModelState.DownloadingState.MangaSaved
                } catch (e: Exception) {
                    state.value = ViewModelState.DownloadingState.MangaSaveError
                }
            }
    }

    private suspend fun fetchManga(mangaId: String) {
        _manga.value = mangaRepository.getMangaWithChapters(mangaId)
        try {
            if (manga.value?.manga?.downloaded == true) {
                coverBitmap.value = mangaLocalStoreService.loadMangaCoverFromFile(mangaId)
            } else {
                coverBitmap.value =
                    mangaLocalStoreService.downloadMangaCoverFromServer(manga.value!!.manga.thumb)
            }
        } catch (e: Exception) {
            coverBitmap.value = null
            Timber.e("fetchManga $e")
        }
    }

    private suspend fun initChapters(mangaId: String) {
        try {
            if (!areChaptersInDb(mangaId)) {
                chapterUseCase.syncChapters(mangaId)
            }

            _chapters.value =
                chapterRepository.getChaptersForManga(mangaId).chapters.map { it.chapter }
        } catch (e: Exception) {
            Timber.e("initChapters $e")
            // TODO: add retry dialog
        }
    }

    private suspend fun areChaptersInDb(mangaId: String): Boolean {
        return try {
            chapterRepository.getChaptersForManga(mangaId).chapters.isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }

    sealed class ViewModelState {
        sealed class DownloadingState : ViewModelState() {
            object MangaIsSaving : DownloadingState()
            object MangaSaved : DownloadingState()
            object MangaSaveError : DownloadingState()
        }

        object RequirePermissionsState : ViewModelState()
        object DefaultState : ViewModelState()
    }
}