package com.vanilaque.mangaque.usecase

import com.vanilaque.mangaque.data.repository.MangaRepository
import com.vanilaque.mangaque.service.PrefManager
import javax.inject.Inject

class MangaUseCase @Inject constructor(
    private val repository: MangaRepository,
    private val prefManager: PrefManager
) {

    suspend fun syncManga() {
        val manga = repository.fetchFromTheServer(prefManager.mangaFeedPage.toString(), "")
        repository.insert(manga)
    }

    suspend fun clearTrash() {
        prefManager.beenAppCompletelyClosed = true
        prefManager.mangaFeedPage = 1
        repository.clearTrash()
    }
}