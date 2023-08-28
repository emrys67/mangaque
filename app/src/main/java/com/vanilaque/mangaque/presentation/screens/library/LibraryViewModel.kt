package com.vanilaque.mangaque.presentation.components.screens.library

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanilaque.mangaque.data.model.Manga
import com.vanilaque.mangaque.presentation.components.ChooseBox
import com.vanilaque.mangaque.service.StateManager
import com.vanilaque.mangareader.data.repository.impl.MangaRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val mangaRepositoryImpl: MangaRepositoryImpl,
) : ViewModel() {
    val chosenBox = mutableStateOf(ChooseBox.DOWNLOADED)
    //lateinit var provider: Provider
    val manga: MutableState<MutableList<Manga>> = mutableStateOf(mutableListOf())

    init {
        StateManager.setShowBottomTopBars(true)
        getManga()
    }

//    suspend fun getProvider() {
//        //provider = providerRepositoryImpl.getProvidersFromServer()[1]
//    }

    fun getManga() {
        viewModelScope.launch {
            //getProvider()
//            manga.value.addAll(webtoonRepositoryImpl.getAllWebtoons()
//                .filter { !it.coverURL.isNullOrEmpty() }.toMutableList())
        }
    }


    fun onLikeMangaClick(mangaItem: Manga, index: Int) {
        viewModelScope.launch {
            val currentWebtoons = manga.value.toMutableList()

//            if (mangaItem.isInFavorites) {
//                favoriteWebtoonRepositoryImpl.insertWebtoon(FavoriteWebtoon(slug = mangaItem.mangaSlug))
//            } else {
//                favoriteWebtoonRepositoryImpl.deleteWebtoon(FavoriteWebtoon(slug = mangaItem.mangaSlug))
//            }

            currentWebtoons[index] = mangaItem.copy(isInFavorites = !mangaItem.isInFavorites)
            manga.value = currentWebtoons
        }
    }
}