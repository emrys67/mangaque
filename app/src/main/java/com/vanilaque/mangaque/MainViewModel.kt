package com.vanilaque.mangareader

import androidx.lifecycle.ViewModel
import com.vanilaque.mangaque.api.MangaVerseApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val api: MangaVerseApi
) : ViewModel() {

}