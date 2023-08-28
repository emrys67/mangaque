package com.vanilaque.mangaque.usecase

import com.vanilaque.mangaque.service.PrefManager
import com.vanilaque.mangareader.data.repository.MangaRepository
import javax.inject.Inject

class MangaUseCase @Inject constructor(
    private val repository: MangaRepository,
    private val prefManager: PrefManager
) {

    suspend fun syncManga() {
        val manga = repository.fetchFromTheServer(prefManager.mangaFeedPage.toString(), "")
        repository.insert(manga)
    }
}