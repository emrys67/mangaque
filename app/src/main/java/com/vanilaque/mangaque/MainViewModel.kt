package com.vanilaque.mangaque

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.vanilaque.mangaque.data.repository.MangaRepository
import com.vanilaque.mangaque.service.PrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val prefManager: PrefManager,
    val mangaRepository: MangaRepository,
) : ViewModel() {
    val searchText: MutableState<String> = mutableStateOf("")
    val isSearchFieldFocused: MutableState<Boolean> = mutableStateOf(false)

    @OptIn(DelicateCoroutinesApi::class)
    fun clearTrash() {
        prefManager.beenAppCompletelyClosed = true
        prefManager.mangaFeedPage = 1
        GlobalScope.launch(Dispatchers.IO) {
            mangaRepository.clearTrash()
        }
    }
}