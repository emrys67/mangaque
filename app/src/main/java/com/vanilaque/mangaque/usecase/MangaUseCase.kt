package com.vanilaque.mangaque.usecase

import com.vanilaque.mangaque.data.model.MangaWithChapters
import com.vanilaque.mangaque.service.MangaLocalStoreService
import com.vanilaque.mangaque.service.PrefManager
import com.vanilaque.mangareader.data.repository.MangaRepository
import javax.inject.Inject

class MangaUseCase @Inject constructor(
    private val repository: MangaRepository,
    private val prefManager: PrefManager,
    private val mangaLocalStoreService: MangaLocalStoreService
) {

    suspend fun syncManga() {
        val manga = repository.fetchFromTheServer(prefManager.mangaFeedPage.toString(), "")
        repository.insert(manga)
    }

    suspend fun saveMangaToLocalStorage(mangaWithChapters: MangaWithChapters){
        mangaLocalStoreService.saveManga(mangaWithChapters)
    }

    suspend fun fetchMangaFromLocalStorage(mangaWithChapters: MangaWithChapters){

    }
}