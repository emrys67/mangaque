package com.vanilaque.mangaque.usecase

import com.vanilaque.mangaque.service.PrefManager
import com.vanilaque.mangareader.data.repository.ChapterRepository
import javax.inject.Inject

class ChapterUseCase @Inject constructor(
    private val chapterRepository: ChapterRepository,
    private val prefManager: PrefManager
) {
    suspend fun syncChapters(mangaId: String){
        val chapters = chapterRepository.fetchFromTheServer(mangaId)
        chapterRepository.insert(chapters)
    }
}