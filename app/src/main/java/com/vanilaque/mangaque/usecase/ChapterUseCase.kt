package com.vanilaque.mangaque.usecase

import com.vanilaque.mangaque.data.repository.ChapterFrameRepository
import com.vanilaque.mangaque.data.repository.ChapterRepository
import javax.inject.Inject

class ChapterUseCase @Inject constructor(
    private val chapterRepository: ChapterRepository,
    private val chapterFrameRepository: ChapterFrameRepository
) {
    suspend fun syncChapters(mangaId: String) {
        val chapters = chapterRepository.fetchFromTheServer(mangaId)
        for ((index, chapter) in chapters.withIndex()) {
            chapter.index = index
        }
        chapterRepository.insert(chapters)
        chapters.forEach {
            val chapterFrames = chapterFrameRepository.fetchFromTheServer(it.id)
            chapterFrameRepository.insertAll(chapterFrames)
        }
    }
}