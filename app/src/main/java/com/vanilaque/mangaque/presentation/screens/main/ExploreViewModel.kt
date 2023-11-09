package com.vanilaque.mangaque.presentation.screens.main

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vanilaque.mangaque.data.model.Manga
import com.vanilaque.mangaque.service.PrefManager
import com.vanilaque.mangaque.service.StateManager
import com.vanilaque.mangaque.usecase.MangaUseCase
import com.vanilaque.mangareader.data.repository.impl.MangaRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val mangaRepository: MangaRepositoryImpl,
    private val mangaUseCase: MangaUseCase,
    private val prefManager: PrefManager
) : ViewModel() {
    val state = mutableStateOf<ViewModelState>(ViewModelState.DefaultState)
    val mangaPaged: Flow<PagingData<Manga>> =
        mangaRepository.getAllPaged().cachedIn(viewModelScope)
    val hasCalledOnScrolledToEnd = mutableStateOf(false)

    init {
        StateManager.setShowBottomTopBars(true)
    }

    fun fetchMangaFromTheServer() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.e("", "fetch more manga from server")
                prefManager.mangaFeedPage = prefManager.mangaFeedPage + 1
                mangaUseCase.syncManga()
                hasCalledOnScrolledToEnd.value = false
            } catch (exception: Exception) {
                state.value = ViewModelState.ErrorState(exception)
            }
        }
    }

    fun refreshManga() {
        viewModelScope.launch {
            prefManager.mangaFeedPage = 1
            fetchMangaFromTheServer()
        }
    }

    fun onLikeMangaClick(mangaItem: Manga, index: Int) {
        viewModelScope.launch {
            mangaRepository.update(
                mangaItem.copy(
                    isInFavorites = !mangaItem.isInFavorites,
                    addedToFavoritesAt = System.currentTimeMillis()
                )
            )
        }
    }

    open class ViewModelState() {
        object DefaultState : ViewModelState()
        class ErrorState(val e: Exception) : ViewModelState()
    }
}