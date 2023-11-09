package com.vanilaque.mangaque.presentation.components.screens.library

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vanilaque.mangaque.data.model.Manga
import com.vanilaque.mangaque.presentation.components.ChooseBox
import com.vanilaque.mangaque.service.StateManager
import com.vanilaque.mangareader.data.repository.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val mangaRepository: MangaRepository,
) : ViewModel() {
    val chosenBox = mutableStateOf(ChooseBox.DOWNLOADED)
    val favoriteManga: Flow<PagingData<Manga>> =
        mangaRepository.getAllFavoritePaged().cachedIn(viewModelScope)
    val savedManga: Flow<PagingData<Manga>> =
        mangaRepository.getAllSavedPaged().cachedIn(viewModelScope)

    init {
        StateManager.setShowBottomTopBars(true)
    }

    fun onLikeMangaClick(mangaItem: Manga) {
        viewModelScope.launch {
            mangaItem.let {
                val updatedMangaItem = it.copy(isInFavorites = !it.isInFavorites, addedToFavoritesAt = System.currentTimeMillis())
                mangaRepository.update(updatedMangaItem)
            }
        }
    }
}